package onboardingMarcos.tinelli.service;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeRepository;
import onboardingMarcos.tinelli.requests.NfePostRequestBody;
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
    UUID uuid;
    do {
      uuid = UUID.randomUUID();
    } while (nfeRepository.findById(uuid).isPresent());
    return nfeRepository.save(
        new Nfe(
            uuid,
            nfePostRequestBody.getNumber(),
            nfePostRequestBody.getDate(),
            nfePostRequestBody.getValue()
        )
    );
  }
}
