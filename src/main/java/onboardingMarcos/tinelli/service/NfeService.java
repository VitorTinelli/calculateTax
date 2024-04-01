package onboardingMarcos.tinelli.service;

import java.util.List;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.repository.NfeRepository;

public class NfeService {

  private final NfeRepository nfeRepository;

  public NfeService(NfeRepository nfeRepository) {
    this.nfeRepository = nfeRepository;
  }

  public List<Nfe> listAll() {
    return nfeRepository.findAll();
  }
}
