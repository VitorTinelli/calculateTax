package onboardingMarcos.tinelli.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NfeRepository extends JpaRepository<Nfe, UUID> {

  Optional<Nfe> findByNumber(Long number);
  List<Nfe> findByDateBetween(LocalDate start, LocalDate end);
}
