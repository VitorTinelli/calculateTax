package onboardingMarcos.tinelli.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.*;
import onboardingMarcos.tinelli.controller.SelicController;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeTaxRepository;
import onboardingMarcos.tinelli.requests.NfeTaxYearMonthRequestBody;
import onboardingMarcos.tinelli.service.NfeService;
import onboardingMarcos.tinelli.service.NfeTaxService;
import onboardingMarcos.tinelli.service.TaxesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class NfeTaxServiceTest {
  Nfe nfe;
  Taxes tax;
  NfeTax nfeTax;

  @InjectMocks
  private NfeTaxService nfeTaxService;

  @Mock
  private NfeTaxRepository nfeTaxRepository;

  @Mock
  private NfeService nfeService;

  @Mock
  private TaxesService taxesService;

  @Mock
  private SelicController SelicController;

  @BeforeEach
  void setUp() {
    UUID id = UUID.randomUUID();
    nfe = new Nfe(id, 12345678910L, LocalDate.now(), 198.00D);
    tax = new Taxes(id, "ICMS", 17.0D);
    nfeTax = new NfeTax(id, nfe, tax, 198.00D, 0.00D, "January", 2022);
  }

  @Test
  @DisplayName("List all returns a NFE list when successful")
  void listAll_ReturnAllNfeTax_WhenSuccessful() {
    when(nfeTaxRepository.findAll()).thenReturn(List.of(nfeTax));
    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.listAll();
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  @DisplayName("List all returns an empty list when no NFE is found")
  void listAll_ReturnEmptyList_WhenNfeNotFound() {
    when(nfeTaxRepository.findAll()).thenReturn(Collections.emptyList());
    Assertions.assertTrue(nfeTaxService.listAll().getBody().isEmpty());
  }

  @Test
  @DisplayName("List by Nfe ID return a list of NfeTax when successful")
  void listByNfeId_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeService.findByIdOrThrowBadRequestException(nfe.getId())).thenReturn(nfe);
    when(nfeTaxRepository.findByNfe(nfe)).thenReturn(List.of(nfeTax));

    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.getByNfeId(nfe.getId().toString());
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  @DisplayName("List by Nfe ID return an empty list when no NfeTax is found")
  void listByNfeId_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeService.findByIdOrThrowBadRequestException(any(UUID.class))).thenThrow(
        new BadRequestException("NFe not Found, Please verify the provided ID"));

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.getByNfeId(nfe.getId().toString()));
  }

  @Test
  @DisplayName("List by Nfe Year return a list of NfeTax when successful")
  void listByNfeYear_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeTaxRepository.findByYear(any(Long.class))).thenReturn(List.of(nfeTax));
    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.getByNfeYear(2001L);
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  @DisplayName("List by Nfe Year return a empty list of NfeTax when no NfeTax is found")
  void listByNfeYear_ReturnEmptyList_WhenNfeTaxNotFound() {
    when(nfeTaxRepository.findByYear(any(Long.class))).thenReturn(Collections.emptyList());
    Assertions.assertTrue(
        Objects.requireNonNull(nfeTaxService.getByNfeYear(2001L).getBody()).isEmpty());
  }

  @Test
  @DisplayName("List by Nfe Month and Year return a list of NfeTax when successful")
  void listByNfeMonthAndYear_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeTaxRepository.findByMonthAndYear(any(String.class), any(Long.class)))
        .thenReturn(List.of(nfeTax));
    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.getByNfeMonthAndYear(
        new NfeTaxYearMonthRequestBody("January", 2022L));
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  @DisplayName("List by Nfe Month and Year return a empty list of NfeTax when no NfeTax is found")
  void listByNfeMonthAndYear_ReturnEmptyList_WhenNfeTaxNotFound() {
    when(nfeTaxRepository.findByMonthAndYear(any(String.class), any(Long.class)))
        .thenReturn(Collections.emptyList());
    Assertions.assertTrue(
        Objects.requireNonNull(nfeTaxService.getByNfeMonthAndYear(
            new NfeTaxYearMonthRequestBody("January", 2022L)).getBody()).isEmpty());
  }

  @Test
  @DisplayName("Find by ID returns a NfeTax when successful")
  void findById_ReturnNfeTax_WhenSuccessful() {
    when(nfeTaxRepository.findById(nfeTax.getId())).thenReturn(Optional.of(nfeTax));
    ResponseEntity<NfeTax> savedNfeTax = nfeTaxService.findByIdOrThrowBadRequestException(
        nfeTax.getId());
    Assertions.assertEquals(nfeTax, savedNfeTax.getBody());
  }

  @Test
  @DisplayName("Find by ID throws BadRequestException when no NfeTax is found")
  void findById_ThrowBadRequestException_WhenNfeTaxNotFound() {
    when(nfeTaxRepository.findById(nfeTax.getId())).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.findByIdOrThrowBadRequestException(nfeTax.getId()));
  }

  @Test
  @DisplayName("Post returns a NfeTax when successful")
  void post_ReturnNfeTax_WhenSuccessful() {
    when(nfeService.listAll()).thenReturn(new ArrayList<>(List.of(nfe)));
    when(taxesService.listAll()).thenReturn(List.of(tax));
    when(SelicController.getSelicPerMonth()).thenReturn(0.0D);
    when(nfeTaxRepository.findByNfeAndTaxes(nfe, tax)).thenReturn(java.util.Optional.empty());

    when(nfeTaxRepository.save(any(NfeTax.class))).thenReturn(nfeTax);

    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.postEveryNfeWithoutTax();
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  @DisplayName("Post throws BadRequestException when no NFE found")
  void post_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeService.listAll()).thenReturn(Collections.emptyList());
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.postEveryNfeWithoutTax());
  }

  @Test
  @DisplayName("Post returns an empty list when every NFE has every TAX")
  void post_ReturnEmptyList_WhenEveryNfeHasTax() {
    when(nfeService.listAll()).thenReturn(new ArrayList<>(List.of(nfe)));
    when(taxesService.listAll()).thenReturn(List.of(tax));
    when(nfeTaxRepository.findByNfeAndTaxes(nfe, tax)).thenReturn(Optional.of(nfeTax));
    
    Assertions.assertTrue(nfeTaxService.postEveryNfeWithoutTax().getBody().isEmpty());
    verify(nfeTaxRepository).findByNfeAndTaxes(nfe, tax);
    verify(nfeTaxRepository, never()).save(any(NfeTax.class));
  }

  @Test
  @DisplayName("Post returns a NfeTax when successful by date gap")
  void post_ReturnNfeTax_WhenSuccessfulByDateGap() {
    when(nfeService.findByTimeGap(any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(new ArrayList<>(List.of(nfe)));
    when(taxesService.listAll()).thenReturn(List.of(tax));
    when(SelicController.getSelicPerMonth()).thenReturn(9.0D);
    when(nfeTaxRepository.findByNfeAndTaxes(nfe, tax)).thenReturn(Optional.empty());
    when(nfeTaxRepository.save(any(NfeTax.class))).thenReturn(nfeTax);

    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.postEveryNfeWithoutTaxByDateGap(
        LocalDate.now(), LocalDate.now());
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  @DisplayName("Post returns an empty list when every NFE has every TAX by date gap")
  void post_ReturnEmptyList_WhenEveryNfeHasTaxByDateGap() {
    when(nfeService.findByTimeGap(any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(new ArrayList<>(List.of(nfe)));
    when(taxesService.listAll()).thenReturn(List.of(tax));
    when(nfeTaxRepository.findByNfeAndTaxes(nfe, tax)).thenReturn(Optional.of(nfeTax));

    Assertions.assertTrue(
        nfeTaxService.postEveryNfeWithoutTaxByDateGap(LocalDate.now(), LocalDate.now())
            .getBody().isEmpty());
    verify(nfeTaxRepository).findByNfeAndTaxes(nfe, tax);
    verify(nfeTaxRepository, never()).save(any(NfeTax.class));
  }

  @Test
  @DisplayName("Post throws BadRequestException when no NFE found by date gap")
  void post_ThrowBadRequestException_WhenNfeNotFoundByDateGap() {
    when(nfeService.findByTimeGap(any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(Collections.emptyList());
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.postEveryNfeWithoutTaxByDateGap(LocalDate.now(), LocalDate.now()));
  }

  @Test
  @DisplayName("Delete all by Nfe ID returns no content when successful")
  void deleteAllByNfeID_ReturnNoContent_WhenSuccessful() {
    when(nfeService.findByIdOrThrowBadRequestException(nfe.getId())).thenReturn(nfe);
    when(nfeTaxRepository.findByNfe(nfe)).thenReturn(List.of(nfeTax));
    nfeTaxService.deleteAllByNfeID(nfe.getId());
    verify(nfeTaxRepository).deleteAll(List.of(nfeTax));
  }

  @Test
  @DisplayName("Delete all by Nfe ID throws BadRequestException when no NfeTax is found")
  void deleteAllByNfeID_ThrowBadRequestException_WhenNfeTaxNotFound() {
    when(nfeService.findByIdOrThrowBadRequestException(nfe.getId())).thenReturn(nfe);
    when(nfeTaxRepository.findByNfe(nfe)).thenReturn(Collections.emptyList());
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.deleteAllByNfeID(nfe.getId()));
  }

  @Test
  @DisplayName("Delete All by Nfe Id Thwors BadRequestException when Nfe not found")
  void deleteAllByNfeID_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeService.findByIdOrThrowBadRequestException(any(UUID.class))).thenThrow(
        new BadRequestException("NFe not Found, Please verify the provided ID"));
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.deleteAllByNfeID(nfe.getId()));
  }

  @Test
  @DisplayName("Delete by NfeTax ID deletes the NfeTax when successful")
  void deleteByNfeTaxId_ReturnNoContent_WhenSuccessful() {
    when(nfeTaxRepository.findById(nfeTax.getId())).thenReturn(Optional.of(nfeTax));
    nfeTaxService.deleteByNfeTaxIdOrThrowBadRequestException(nfeTax.getId());
    verify(nfeTaxRepository).deleteById(nfeTax.getId());
  }

  @Test
  @DisplayName("Delete by NfeTax ID throws BadRequestException when no NfeTax is found")
  void deleteByNfeTaxId_ThrowBadRequestException_WhenNfeTaxNotFound() {
    when(nfeTaxRepository.findById(nfeTax.getId())).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.deleteByNfeTaxIdOrThrowBadRequestException(nfeTax.getId()));
  }
}
