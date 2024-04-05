package onboardingMarcos.tinelli.repository;

import java.util.UUID;
import onboardingMarcos.tinelli.domain.Taxes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxesRepository extends JpaRepository<Taxes, UUID> {

}
