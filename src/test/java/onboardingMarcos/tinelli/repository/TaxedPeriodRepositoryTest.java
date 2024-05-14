package onboardingMarcos.tinelli.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxedPeriod;
import onboardingMarcos.tinelli.domain.Taxes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaxedPeriodRepositoryTest {

  UUID id;
  Taxes taxes;
  TaxedPeriod taxedPeriod;

  @Autowired
  private TaxedPeriodRepository taxedPeriodRepository;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    taxedPeriod = new TaxedPeriod(id, BigDecimal.valueOf(1000D), BigDecimal.valueOf(500D),
        LocalDate.now(), taxes);
  }

  @Test
  @DisplayName("findById returns registered TaxCalculation when successful")
  void findById_ReturnTaxCalculation_WhenSuccessful() {
    taxedPeriodRepository.save(taxedPeriod);
    TaxedPeriod foundTaxedPeriod = taxedPeriodRepository.findById(id).get();
    assertThat(foundTaxedPeriod).isNotNull();
    assertThat(foundTaxedPeriod.getId()).isEqualTo(taxedPeriod.getId());
    assertThat(foundTaxedPeriod.getCalculationDate()).isEqualTo(
        taxedPeriod.getCalculationDate());
    assertThat(foundTaxedPeriod.getTax()).isEqualTo(taxedPeriod.getTax());
  }

  @Test
  @DisplayName("Find by calc date returns all registered TaxCalculation in the month when successful")
  void findByCalculationDate_ReturnTaxCalculation_WhenSuccessful() {
    taxedPeriodRepository.save(taxedPeriod);
    assertThat(taxedPeriodRepository.findByCalculationDate(LocalDate.now())).isNotNull();
  }

  @Test
  @DisplayName("save persists TaxCalculation when successful")
  void save_persists_whenSuccessful() {
    TaxedPeriod savedTaxedPeriod = taxedPeriodRepository.save(taxedPeriod);
    assertThat(taxedPeriodRepository.findById(id)).isNotNull();
    assertThat(savedTaxedPeriod).isNotNull();
    assertThat(savedTaxedPeriod.getId()).isEqualTo(taxedPeriod.getId());
    assertThat(savedTaxedPeriod.getCalculationDate()).isEqualTo(
        taxedPeriod.getCalculationDate());
    assertThat(savedTaxedPeriod.getTax()).isEqualTo(taxedPeriod.getTax());
  }

  @Test
  @DisplayName("delete removes TaxCalculation when successful")
  void delete_removesTaxCalculation_whenSuccessful() {
    taxedPeriodRepository.save(taxedPeriod);
    taxedPeriodRepository.delete(taxedPeriod);
    assertThat(taxedPeriodRepository.findById(id)).isEmpty();
  }

  @Test
  @DisplayName("delete all removes a list of TaxCalculation when successful")
  void deleteAll_removesAllTaxCalculation_whenSuccessful() {
    taxedPeriodRepository.save(taxedPeriod);
    taxedPeriodRepository.deleteAll(List.of(taxedPeriod));
    assertThat(taxedPeriodRepository.findById(id).isEmpty());
  }

}
