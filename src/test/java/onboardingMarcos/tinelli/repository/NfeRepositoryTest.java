package onboardingMarcos.tinelli.repository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDate;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NfeRepositoryTest {

  Nfe nfe;
  UUID id;

  @Autowired
  private NfeRepository nfeRepository;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    nfe = new Nfe(id, 12345678910L, LocalDate.now(), 198.00D);
  }

  @Test
  @DisplayName("findById returns registered Nfe when successful")
  void findById_ReturnNfe_WhenSuccessful() {
    nfeRepository.save(nfe);
    Nfe foundNfe = nfeRepository.findById(id).get();
    assertThat(foundNfe).isNotNull();
    assertThat(foundNfe.getNumber()).isEqualTo(nfe.getNumber());
    assertThat(foundNfe.getValue()).isEqualTo(nfe.getValue());
    assertThat(foundNfe.getId()).isEqualTo(nfe.getId());
  }

  @Test
  @DisplayName("find by start and end date returns all registered Nfe in the period when successful")
  void findByDateBetween_ReturnNfe_WhenSuccessful() {
    nfeRepository.save(nfe);
    LocalDate start = LocalDate.now();
    LocalDate end = LocalDate.now().plusDays(1);
    assertThat(nfeRepository.findByDateBetween(start, end)).isNotNull();
  }

  @Test
  @DisplayName("save persists Nfe when successful")
  void save_persists_whenSuccessful() {
    Nfe savedNfe = nfeRepository.save(nfe);
    assertThat(nfeRepository.findById(id)).isNotNull();
    assertThat(savedNfe).isNotNull();
    assertThat(savedNfe.getNumber()).isEqualTo(nfe.getNumber());
    assertThat(savedNfe.getValue()).isEqualTo(nfe.getValue());
  }

  @Test
  @DisplayName("delete removes Nfe when successful")
  void delete_removesNfe_WhenSuccessful() {
    nfeRepository.save(nfe);
    assertThat(nfeRepository.findById(id)).isNotEmpty();
    nfeRepository.delete(nfe);
    assertThat(nfeRepository.findById(id)).isEmpty();
  }

  @Test
  @DisplayName("put updates Nfe when successful")
  void replace_updatesNfe_WhenSuccessful() {
    nfeRepository.save(nfe);
    Nfe newNfe = new Nfe(nfe.getId(), 12345678911L, LocalDate.now(), 198.00D);
    nfeRepository.save(newNfe);
    Nfe foundNfe = nfeRepository.findById(id).get();
    assertThat(foundNfe).isNotNull();
    assertThat(foundNfe.getNumber()).isEqualTo(newNfe.getNumber());
    assertThat(foundNfe.getValue()).isEqualTo(newNfe.getValue());
  }
}
