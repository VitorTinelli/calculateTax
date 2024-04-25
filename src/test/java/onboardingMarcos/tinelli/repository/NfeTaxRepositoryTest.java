package onboardingMarcos.tinelli.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.domain.Taxes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NfeTaxRepositoryTest {

  NfeTax nfeTax;
  Nfe nfe;
  Taxes tax;
  UUID id;

  @Autowired
  private NfeTaxRepository nfeTaxRepository;

  @Autowired
  private NfeRepository nfeRepository;

  @Autowired
  private TaxesRepository taxesRepository;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    nfe = new Nfe(id, 12345678910L, LocalDate.now(), 198.00D);
    tax = new Taxes(id, "ICMS", 17.0D);

    nfe = nfeRepository.save(nfe);
    tax = taxesRepository.save(tax);

    nfeTax = new NfeTax(id, nfe, tax, 198.00D, 0.00D, "April", 2222);
  }

  @Test
  @DisplayName("findById returns registered NfeTax when successful")
  void findById_ReturnNfeTax_WhenSuccessful() {
    nfeTaxRepository.save(nfeTax);
    NfeTax foundNfeTax = nfeTaxRepository.findById(nfeTax.getId()).get();
    assertThat(foundNfeTax).isNotNull();
    assertThat(foundNfeTax.getNfe()).isEqualTo(nfe);
    assertThat(foundNfeTax.getTaxes()).isEqualTo(tax);
  }

  @Test
  @DisplayName("Find by Nfe returns registered NfeTax when successful")
  void findByNfe_ReturnNfeTax_WhenSuccessful() {
    nfeTaxRepository.save(nfeTax);
    List<NfeTax> foundNfeTax = nfeTaxRepository.findByNfe(nfe);
    assertThat(foundNfeTax).isNotNull();
    assertThat(foundNfeTax.get(0).getNfe()).isEqualTo(nfe);
    assertThat(foundNfeTax.get(0).getTaxes()).isEqualTo(tax);
  }

  @Test
  @DisplayName("Find by Nfe and Tax returns registered NfeTax when successful")
  void findByNfeAndTaxes_ReturnNfeTax_WhenSuccessful() {
    nfeTaxRepository.save(nfeTax);
    NfeTax foundNfeTax = nfeTaxRepository.findByNfeAndTaxes(nfe, tax).get();
    assertThat(foundNfeTax).isNotNull();
    assertThat(foundNfeTax.getNfe()).isEqualTo(nfe);
    assertThat(foundNfeTax.getTaxes()).isEqualTo(tax);
  }

  @Test
  @DisplayName("Find by Year returns registered NfeTax when successful")
  void findByYear_ReturnNfeTax_WhenSuccessful() {
    nfeTaxRepository.save(nfeTax);
    List<NfeTax> foundNfeTax = nfeTaxRepository.findByYear(2222L);
    assertThat(foundNfeTax).isNotNull();
    assertThat(foundNfeTax.get(0).getNfe()).isEqualTo(nfe);
    assertThat(foundNfeTax.get(0).getTaxes()).isEqualTo(tax);
  }

  @Test
  @DisplayName("Find by Month and Year returns registered NfeTax when successful")
  void findByMonthAndYear_ReturnNfeTax_WhenSuccessful() {
    nfeTaxRepository.save(nfeTax);
    List<NfeTax> foundNfeTax = nfeTaxRepository.findByMonthAndYear("April", 2222L);
    assertThat(foundNfeTax).isNotNull();
    assertThat(foundNfeTax.get(0).getNfe()).isEqualTo(nfe);
    assertThat(foundNfeTax.get(0).getTaxes()).isEqualTo(tax);
  }

  @Test
  @DisplayName("save persists NfeTax when successful")
  void save_persistsNfeTax_WhenSuccessful() {
    NfeTax savedNfeTax = nfeTaxRepository.save(nfeTax);
    assertThat(savedNfeTax).isNotNull();
    assertThat(savedNfeTax.getNfe()).isEqualTo(nfe);
    assertThat(savedNfeTax.getTaxes()).isEqualTo(tax);
  }

  @Test
  @DisplayName("delete removes by NfeTax ID delete the NfeTax when successful")
  void delete_removesByNfeTaxId_WhenSuccessful() {
    nfeTaxRepository.save(nfeTax);
    assertThat(nfeTaxRepository.findById(id)).isNotEmpty();
    nfeTaxRepository.delete(nfeTax);
    assertThat(nfeTaxRepository.findById(id)).isEmpty();
  }

  @Test
  @DisplayName("Delete removes by all NfeTax registered with the same Nfe ID when successful")
  void delete_removesByNfeId_whenSuccessful() {
    nfeTaxRepository.save(nfeTax);
    assertThat(nfeTaxRepository.findById(id)).isNotEmpty();
    List<NfeTax> nfeTax = nfeTaxRepository.findByNfe(nfe);
    nfeTaxRepository.deleteAll(nfeTax);
    assertThat(nfeTaxRepository.findById(id)).isEmpty();
  }
}
