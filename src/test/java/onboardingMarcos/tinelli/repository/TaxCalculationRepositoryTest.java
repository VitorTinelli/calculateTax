package onboardingMarcos.tinelli.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxCalculation;
import onboardingMarcos.tinelli.domain.Taxes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaxCalculationRepositoryTest {

  UUID id;
  Taxes taxes;
  TaxCalculation taxCalculation;

  @Autowired
  private TaxCalculationRepository taxCalculationRepository;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    taxCalculation = new TaxCalculation(id, BigDecimal.valueOf(1000D), BigDecimal.valueOf(500D),
        LocalDate.now(), taxes);
  }

  @Test
  @DisplayName("findById returns registered TaxCalculation when successful")
  void findById_ReturnTaxCalculation_WhenSuccessful() {
    taxCalculationRepository.save(taxCalculation);
    TaxCalculation foundTaxCalculation = taxCalculationRepository.findById(id).get();
    assertThat(foundTaxCalculation).isNotNull();
    assertThat(foundTaxCalculation.getId()).isEqualTo(taxCalculation.getId());
    assertThat(foundTaxCalculation.getCalculationDate()).isEqualTo(
        taxCalculation.getCalculationDate());
    assertThat(foundTaxCalculation.getTax()).isEqualTo(taxCalculation.getTax());
  }

  @Test
  @DisplayName("Find by calc date returns all registered TaxCalculation in the month when successful")
  void findByCalculationDate_ReturnTaxCalculation_WhenSuccessful() {
    taxCalculationRepository.save(taxCalculation);
    assertThat(taxCalculationRepository.findByCalculationDate(LocalDate.now())).isNotNull();
  }

  @Test
  @DisplayName("save persists TaxCalculation when successful")
  void save_persists_whenSuccessful() {
    TaxCalculation savedTaxCalculation = taxCalculationRepository.save(taxCalculation);
    assertThat(taxCalculationRepository.findById(id)).isNotNull();
    assertThat(savedTaxCalculation).isNotNull();
    assertThat(savedTaxCalculation.getId()).isEqualTo(taxCalculation.getId());
    assertThat(savedTaxCalculation.getCalculationDate()).isEqualTo(
        taxCalculation.getCalculationDate());
    assertThat(savedTaxCalculation.getTax()).isEqualTo(taxCalculation.getTax());
  }

  @Test
  @DisplayName("delete removes TaxCalculation when successful")
  void delete_removesTaxCalculation_whenSuccessful() {
    taxCalculationRepository.save(taxCalculation);
    taxCalculationRepository.delete(taxCalculation);
    assertThat(taxCalculationRepository.findById(id)).isEmpty();
  }

  @Test
  @DisplayName("delete all removes a list of TaxCalculation when successful")
  void deleteAll_removesAllTaxCalculation_whenSuccessful() {
    taxCalculationRepository.save(taxCalculation);
    taxCalculationRepository.deleteAll(List.of(taxCalculation));
    assertThat(taxCalculationRepository.findById(id).isEmpty());
  }

}
