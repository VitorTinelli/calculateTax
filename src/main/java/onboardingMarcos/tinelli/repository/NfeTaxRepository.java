package onboardingMarcos.tinelli.repository;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NfeTaxRepository extends JpaRepository<NfeTax, UUID> {

  List<NfeTax> findByNfe(Nfe nfe);
}
