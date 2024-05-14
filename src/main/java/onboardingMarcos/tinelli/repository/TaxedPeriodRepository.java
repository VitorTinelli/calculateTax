package onboardingMarcos.tinelli.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxedPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxedPeriodRepository extends JpaRepository<TaxedPeriod, UUID> {

  List<TaxedPeriod> findByCalculationDate(LocalDate date);
}
