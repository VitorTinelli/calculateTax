package onboardingMarcos.tinelli.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Taxes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaxesRepositoryTest {

  Taxes taxes;
  UUID id;
  @Autowired
  private TaxesRepository taxesRepository;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    taxes = new Taxes(id, "ICC", 10.4D);
  }

  @Test
  @DisplayName("findById returns all registered Taxes when successful")
  void findById_ReturnTaxes_WhenSuccessful() {
    taxesRepository.save(taxes);
    Optional<Taxes> foundTaxes = taxesRepository.findById(id);
    assertThat(foundTaxes).isNotEmpty();
    assertThat(foundTaxes.get().getId()).isNotNull();
    assertThat(foundTaxes.get().getAliquot()).isEqualTo(taxes.getAliquot());
  }

  @Test
  @DisplayName("save persists Taxes when successful")
  void save_persists_whenSuccessful() {
    Taxes savedTaxes = taxesRepository.save(taxes);
    assertThat(savedTaxes).isNotNull();
    assertThat(savedTaxes.getId()).isNotNull();
    assertThat(savedTaxes.getAliquot()).isEqualTo(taxes.getAliquot());
  }

  @Test
  @DisplayName("delete removes Taxes when successful")
  void delete_removesTaxes_WhenSuccessful() {
    taxesRepository.save(taxes);
    assertThat(taxesRepository.findById(id)).isNotEmpty();
    taxesRepository.delete(taxes);
    Optional<Taxes> foundTaxes = taxesRepository.findById(id);
    assertThat(foundTaxes).isEmpty();
  }

  @Test
  @DisplayName("put updates Taxes when successful")
  void put_updatesTaxes_WhenSuccessful() {
    taxesRepository.save(taxes);
    Taxes newTaxes = new Taxes(taxes.getId(), "IRRF", 7.5D);
    taxesRepository.save(newTaxes);
    Optional<Taxes> foundTaxes = taxesRepository.findById(id);
    assertThat(foundTaxes).isNotEmpty();
    assertThat(foundTaxes.get().getName().equals(newTaxes.getName()));
    assertThat(foundTaxes.get().getAliquot()).isEqualTo(newTaxes.getAliquot());
  }
}