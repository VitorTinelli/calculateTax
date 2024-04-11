package onboardingMarcos.tinelli.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.controller.SelicController;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeTaxRepository;
import onboardingMarcos.tinelli.service.NfeService;
import onboardingMarcos.tinelli.service.NfeTaxService;
import onboardingMarcos.tinelli.service.TaxesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
  void listAll_ReturnAllNfeTax_WhenSuccessful() {
    when(nfeTaxRepository.findAll()).thenReturn(List.of(nfeTax));
    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.listAll();
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  void listAll_ReturnEmptyList_WhenNfeNotFound() {
    when(nfeTaxRepository.findAll()).thenReturn(Collections.emptyList());
    Assertions.assertTrue(nfeTaxService.listAll().getBody().isEmpty());
  }

  @Test
  void listByNfeId_ReturnListOfNfeTax_WhenSuccessful() {
    when(nfeService.findByIdOrThrowBadRequestException(nfe.getId())).thenReturn(nfe);
    when(nfeTaxRepository.findByNfe(nfe)).thenReturn(List.of(nfeTax));

    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.getByNfeId(nfe.getId().toString());
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  void listByNfeId_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeService.findByIdOrThrowBadRequestException(any(UUID.class))).thenThrow(
        new BadRequestException("NFe not Found, Please verify the provided ID"));

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.getByNfeId(nfe.getId().toString()));
  }

  @Test
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
  void post_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeService.listAll()).thenReturn(Collections.emptyList());
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.postEveryNfeWithoutTax());
  }
}
