package vitor.tinelli.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitor.tinelli.domain.Nfe;

@Repository
public interface NfeRepository extends JpaRepository<Nfe, UUID> {

}
