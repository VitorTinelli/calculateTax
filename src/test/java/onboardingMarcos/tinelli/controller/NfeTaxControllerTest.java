package onboardingMarcos.tinelli.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.dto.SelicDTO;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.NfeTaxYearMonthRequestBody;
import onboardingMarcos.tinelli.service.NfeTaxService;
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
public class NfeTaxControllerTest {

  Nfe nfe;
  NfeTax nfeTax;
  Taxes tax;
  SelicDTO selicDTO;
  NfeTaxYearMonthRequestBody nfeTaxYearMonthRequestBody;

  @InjectMocks
  private NfeTaxController nfeTaxController;

  @Mock
  private NfeTaxService nfeTaxService;

  @BeforeEach
  void setUp() {
    UUID id = UUID.randomUUID();
    nfe = new Nfe(id, 12345678910L, LocalDate.now(), 198.00D);
    tax = new Taxes(id, "ICMS", 17);
    selicDTO = new SelicDTO("Selic", "0.98");
    nfeTax = new NfeTax(
        id,
        nfe,
        tax,
        200.00D,
        2.000D,
        "janeiro",
        2022L
    );
    nfeTaxYearMonthRequestBody = new NfeTaxYearMonthRequestBody("janeiro", 2022L);
  }

  @Test
  @DisplayName("listAll returns list of nfeTax when successful")
  void listAll_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeTaxService.listAll()).thenReturn(
        ResponseEntity.ok(List.of(nfeTax)));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.listAll();
    Assertions.assertEquals(nfeTaxList.getBody(), List.of(nfeTax));
    Assertions.assertEquals(nfeTaxList.getStatusCodeValue(), 200);
    verify(nfeTaxService).listAll();
  }

  @Test
  @DisplayName("listAll returns BadRequestException when nfeTax list is empty")
  void listAll_ReturnBadRequestException_WhenNfeListIsEmpty() {
    when(nfeTaxService.listAll()).thenThrow(
        new BadRequestException("No NFEs found"));

    Assertions.assertThrows(BadRequestException.class, () -> nfeTaxController.listAll());
    verify(nfeTaxService).listAll();
  }

  @Test
  @DisplayName("getByNfeId returns list of nfeTax (With the same ID) when successful")
  void getByNfeId_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeTaxService.getByNfeId(nfe.getId().toString())).thenReturn(
        ResponseEntity.ok(List.of(nfeTax)));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.getByNfeId(nfe.getId().toString());
    Assertions.assertEquals(nfeTaxList.getBody(), List.of(nfeTax));
    Assertions.assertEquals(200, nfeTaxList.getStatusCodeValue());
    verify(nfeTaxService).getByNfeId(nfe.getId().toString());
  }

  @Test
  @DisplayName("getByNfeId returns BadRequestException when nfeTax (With the same ID) list is empty")
  void getByNfeId_ReturnBadRequestException_WhenNfeNotExist() {
    when(nfeTaxService.getByNfeId(nfe.getId().toString())).thenThrow(
        new BadRequestException("Nfe not found"));

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxController.getByNfeId(nfe.getId().toString()));
    verify(nfeTaxService).getByNfeId(nfe.getId().toString());
  }

  @Test
  @DisplayName("getByNfeYear returns list of nfeTax (With the same year) when successful")
  void getByNfeYear_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeTaxService.getByNfeYear(any(Long.class))).thenReturn(
        ResponseEntity.ok(List.of(nfeTax)));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.getByNfeYear(2022L);
    Assertions.assertEquals(nfeTaxList.getBody(), List.of(nfeTax));
    Assertions.assertEquals(200, nfeTaxList.getStatusCodeValue());
    verify(nfeTaxService).getByNfeYear(2022L);
  }

  @Test
  @DisplayName("getByNfeYear returns empty list when nfeTax (With the same year) list is empty")
  void getByNfeYear_ReturnEmptyList_WhenNfeNotExist() {
    when(nfeTaxService.getByNfeYear(any(Long.class))).thenReturn(
        ResponseEntity.ok(List.of()));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.getByNfeYear(2022L);
    Assertions.assertEquals(nfeTaxList.getBody(), List.of());
    Assertions.assertEquals(200, nfeTaxList.getStatusCodeValue());
    verify(nfeTaxService).getByNfeYear(2022L);
  }

  @Test
  @DisplayName("list all by year and month returns list of nfeTax (With the same year and month) when successful")
  void getByNfeYearAndMonth_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeTaxService.getByNfeMonthAndYear(any(NfeTaxYearMonthRequestBody.class))).thenReturn(
        ResponseEntity.ok(List.of(nfeTax)));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.getByNfeYearAndMonth(
        nfeTaxYearMonthRequestBody);
    Assertions.assertEquals(nfeTaxList.getBody(), List.of(nfeTax));
    Assertions.assertEquals(200, nfeTaxList.getStatusCodeValue());
    verify(nfeTaxService).getByNfeMonthAndYear(nfeTaxYearMonthRequestBody);
  }

  @Test
  @DisplayName("list all by year and month returns empty list when nfeTax (With the same year and month) list is empty")
  void getByNfeYearAndMonth_ReturnEmptyList_WhenNfeNotExist() {
    when(nfeTaxService.getByNfeMonthAndYear(any(NfeTaxYearMonthRequestBody.class))).thenReturn(
        ResponseEntity.ok(List.of()));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.getByNfeYearAndMonth(
        nfeTaxYearMonthRequestBody);
    Assertions.assertEquals(nfeTaxList.getBody(), List.of());
    Assertions.assertEquals(200, nfeTaxList.getStatusCodeValue());
    verify(nfeTaxService).getByNfeMonthAndYear(nfeTaxYearMonthRequestBody);
  }

  @Test
  @DisplayName("postAllNfeTax returns list of new NfeTax when successful")
  void postAllNfeTax_ReturnListOfNewNfeTax_WhenSuccessful() {
    when(nfeTaxService.postEveryNfeWithoutTax()).thenReturn(
        ResponseEntity.ok(List.of(nfeTax)));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.postAllNfeTax();
    Assertions.assertEquals(nfeTaxList.getBody(), List.of(nfeTax));
    Assertions.assertEquals(200, nfeTaxList.getStatusCodeValue());
    verify(nfeTaxService).postEveryNfeWithoutTax();
  }

  @Test
  @DisplayName("postAllNfeTax returns an empty list when all nfe has already been taxed")
  void postAllNfeTax_ReturnEmptyList_WhenAllNfeAlreadyTaxed() {
    when(nfeTaxService.postEveryNfeWithoutTax()).thenReturn(
        ResponseEntity.ok(List.of()));

    ResponseEntity<List<NfeTax>> nfeTaxList = nfeTaxController.postAllNfeTax();
    Assertions.assertEquals(nfeTaxList.getBody(), List.of());
    Assertions.assertEquals(200, nfeTaxList.getStatusCodeValue());
    verify(nfeTaxService).postEveryNfeWithoutTax();
  }
}
