package vitor.tinelli.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitor.tinelli.domain.Nfe;
import vitor.tinelli.domain.NfeTax;
import vitor.tinelli.domain.Taxes;

@Repository
public interface NfeTaxRepository extends JpaRepository<NfeTax, UUID> {

  List<NfeTax> findByNfe(Nfe nfe);

  Optional<NfeTax> findByNfeAndTaxes(Nfe nfe, Taxes taxes);

  List<NfeTax> findByMonthAndYear(String month, Integer year);

  List<NfeTax> findByYear(Integer year);
}
