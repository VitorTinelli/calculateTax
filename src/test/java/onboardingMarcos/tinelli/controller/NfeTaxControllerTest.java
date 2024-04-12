package onboardingMarcos.tinelli.controller;


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
        2022D
    );
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
}
