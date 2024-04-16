package vitor.tinelli.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitor.tinelli.domain.Taxes;

@Repository
public interface TaxesRepository extends JpaRepository<Taxes, UUID> {

}
