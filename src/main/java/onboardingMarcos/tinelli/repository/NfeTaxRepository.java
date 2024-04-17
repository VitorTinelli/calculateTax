package onboardingMarcos.tinelli.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.domain.Taxes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NfeTaxRepository extends JpaRepository<NfeTax, UUID> {

  List<NfeTax> findByNfe(Nfe nfe);

  Optional<NfeTax> findByNfeAndTaxes(Nfe nfe, Taxes taxes);

  List<NfeTax> findByYear(Long year);

  List<NfeTax> findByMonthAndYear(String month, Long year);
}
