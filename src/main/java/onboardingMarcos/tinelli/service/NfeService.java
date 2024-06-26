package onboardingMarcos.tinelli.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeRepository;
import onboardingMarcos.tinelli.requests.NfePostRequestBody;
import onboardingMarcos.tinelli.requests.NfePutRequestBody;
import onboardingMarcos.tinelli.util.Verifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NfeService {

  private final NfeRepository nfeRepository;

  public NfeService(final NfeRepository nfeRepository) {
    this.nfeRepository = nfeRepository;
  }

  public Page<Nfe> listAll(Pageable pageable) {
    return nfeRepository.findAll(pageable);
  }

  public Nfe findByIdOrThrowBadRequestException(UUID id) {
    return nfeRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "NFe not Found, Please verify the provided ID"));
  }

  public List<Nfe> findByTimePeriod(LocalDate start, LocalDate end) {
    return nfeRepository.findByDateBetween(start, end);
  }

  public Nfe findByNumberOrThrowBadRequestException(Long number) {
    return nfeRepository.findByNumber(number)
        .orElseThrow(() -> new BadRequestException(
            "NFe not Found, Please verify the provided Number"));
  }

  @Transactional
  public Nfe save(NfePostRequestBody nfePostRequestBody) {
    try {
      Verifications.verificationNFEPOST(nfePostRequestBody);
      if (nfeRepository.findByNumber(nfePostRequestBody.getNumber()).isPresent()) {
        throw new BadRequestException("NFe already registered, please verify the provided Number");
      }
      return nfeRepository.save(
          new Nfe(
              UUID.randomUUID(),
              nfePostRequestBody.getNumber(),
              nfePostRequestBody.getDate(),
              nfePostRequestBody.getValue()
          )
      );
    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
  }

  public void delete(UUID uuid) {
    try {
      nfeRepository.delete(findByIdOrThrowBadRequestException(uuid));
    } catch (Exception e) {
      throw new BadRequestException("NFe not found, please verify the provided ID");
    }
  }

  public void replace(NfePutRequestBody nfePutRequestBody) {
    try {
      Verifications.verificationNFEPUT(nfePutRequestBody);
      Nfe savedNfe = findByIdOrThrowBadRequestException(nfePutRequestBody.getId());
      if (nfeRepository.findByNumber(nfePutRequestBody.getNumber()).isPresent()
          && !Objects.equals(savedNfe.getNumber(), nfePutRequestBody.getNumber())) {
        throw new BadRequestException("NFe already registered, please verify the provided Number");
      }
      nfeRepository.save(
          new Nfe(
              savedNfe.getId(),
              nfePutRequestBody.getNumber(),
              nfePutRequestBody.getDate(),
              nfePutRequestBody.getValue()
          )
      );
    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
  }

}
