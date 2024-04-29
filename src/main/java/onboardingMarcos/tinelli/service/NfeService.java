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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NfeService {

  private final NfeRepository nfeRepository;

  public NfeService(final NfeRepository nfeRepository) {
    this.nfeRepository = nfeRepository;
  }

  public List<Nfe> listAll() {
    return nfeRepository.findAll();
  }

  public Nfe findByIdOrThrowBadRequestException(UUID id) {
    return nfeRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "NFe not Found, Please verify the provided ID"));
  }

  public List<Nfe> findByTimeGap(LocalDate start, LocalDate end) {
    return nfeRepository.findByDateBetween(start, end);
  }

  public Nfe findByNumberOrThrowBadRequestException(Long number) {
    return nfeRepository.findByNumber(number)
        .orElseThrow(() -> new BadRequestException(
            "NFe not Found, Please verify the provided Number"));
  }

  public Nfe findByNumberOrReturnNull(Long number) {
    return nfeRepository.findByNumber(number).orElse(null);
  }

  @Transactional
  public Nfe save(NfePostRequestBody nfePostRequestBody) {
    try {
      Verifications.verificationNFEPOST(nfePostRequestBody);
      if (findByNumberOrReturnNull(nfePostRequestBody.getNumber()) != null) {
        throw new BadRequestException("NFe already exists, please verify the provided Number");
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
    findByIdOrThrowBadRequestException(uuid);
    try {
      nfeRepository.deleteById(uuid);
    } catch (Exception e) {
      throw new BadRequestException("NFe not found, please verify the provided ID");
    }
  }

  public void replace(NfePutRequestBody nfePutRequestBody) {
    try {
      Verifications.verificationNFEPUT(nfePutRequestBody);
      Nfe savedNfe = findByIdOrThrowBadRequestException(nfePutRequestBody.getId());
      if (findByNumberOrReturnNull(nfePutRequestBody.getNumber()) != null
          && !Objects.equals(savedNfe.getNumber(), nfePutRequestBody.getNumber())) {
        throw new BadRequestException("NFe already exists, please verify the provided Number");
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
