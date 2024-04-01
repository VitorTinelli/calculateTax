package onboardingMarcos.tinelli.repository;

import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NfeRepository extends JpaRepository<Nfe, UUID> {

}
