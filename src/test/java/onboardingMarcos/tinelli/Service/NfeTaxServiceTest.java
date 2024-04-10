package onboardingMarcos.tinelli.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class NfeTaxServiceTest {

  private static final Logger log = LoggerFactory.getLogger(NfeTaxServiceTest.class);
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

  @BeforeEach
  void setUp() {
    UUID id = UUID.randomUUID();
    nfe = new Nfe(id, 12345678910L, LocalDate.now(), 198.00D);
    tax = new Taxes(id, "ICMS", 17.0D);
    nfeTax = new NfeTax(id, nfe, tax, 198.00D, 0.00D, "January", 2022);
  }

  @Test
  void listAll_ReturnAllNfeTax_WhenSuccessful() {
    when(nfeService.listAll()).thenReturn(new ArrayList<>(List.of(nfe)));
    when(taxesService.listAll()).thenReturn(List.of(tax));
    when(nfeTaxRepository.findByNfe(nfe)).thenReturn(List.of(nfeTax));
    when(nfeTaxRepository.findAll()).thenReturn(List.of(nfeTax));

    ResponseEntity<List<NfeTax>> savedNfeTax = nfeTaxService.postSeparatedByYearAndMonth();
    Assertions.assertEquals(List.of(nfeTax), savedNfeTax.getBody());
  }

  @Test
  void listAll_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeService.listAll()).thenReturn(Collections.emptyList());
    when(taxesService.listAll()).thenReturn(List.of(tax));

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeTaxService.postSeparatedByYearAndMonth());
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
}
