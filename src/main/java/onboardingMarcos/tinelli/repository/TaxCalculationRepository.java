package onboardingMarcos.tinelli.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxCalculationRepository extends JpaRepository<TaxCalculation, UUID> {

  List<TaxCalculation> findByCalculationDate(LocalDate date);
}
