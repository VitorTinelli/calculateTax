package vitor.tinelli.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitor.tinelli.domain.Nfe;

@Repository
public interface NfeRepository extends JpaRepository<Nfe, UUID> {

  List<Nfe> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
