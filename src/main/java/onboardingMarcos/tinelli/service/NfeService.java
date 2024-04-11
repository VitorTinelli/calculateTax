package onboardingMarcos.tinelli.service;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeRepository;
import onboardingMarcos.tinelli.requests.NfePostRequestBody;
import onboardingMarcos.tinelli.requests.NfePutRequestBody;
import onboardingMarcos.tinelli.util.Verifications;
import org.springframework.stereotype.Service;

@Service
public class NfeService {

  private final NfeRepository nfeRepository;

  public NfeService(NfeRepository nfeRepository) {
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

  public Nfe save(NfePostRequestBody nfePostRequestBody) {
    try {
      Verifications.verificationNFEPOST(nfePostRequestBody);
      return nfeRepository.save(
          new Nfe(
              UUID.randomUUID(),
              nfePostRequestBody.getNumber(),
              nfePostRequestBody.getDate(),
              nfePostRequestBody.getValue()
          )
      );
    } catch (Exception exception) {
      throw new BadRequestException("Please verify the provided data");
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
      nfeRepository.save(
          new Nfe(
              savedNfe.getId(),
              nfePutRequestBody.getNumber(),
              nfePutRequestBody.getDate(),
              nfePutRequestBody.getValue()
          )
      );
    } catch (Exception exception) {
      throw new BadRequestException("Please verify the provided data");
    }
  }
}
