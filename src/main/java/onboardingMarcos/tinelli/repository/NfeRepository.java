package onboardingMarcos.tinelli.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NfeRepository extends JpaRepository<Nfe, UUID> {

  List<Nfe> findByDateBetween(LocalDate start, LocalDate end);
}
