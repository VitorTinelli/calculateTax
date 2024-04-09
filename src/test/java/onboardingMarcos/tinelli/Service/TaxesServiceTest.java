package onboardingMarcos.tinelli.Service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.TaxesRepository;
import onboardingMarcos.tinelli.requests.TaxesPostRequestBody;
import onboardingMarcos.tinelli.requests.TaxesPutRequestBody;
import onboardingMarcos.tinelli.service.TaxesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxesServiceTest {

  Taxes tax;
  TaxesPostRequestBody taxesPostRequestBody;
  TaxesPutRequestBody taxesPutRequestBody;

  @InjectMocks
  private TaxesService taxesService;

  @Mock
  private TaxesRepository taxesRepository;

  @BeforeEach
  void setUp() {
    UUID id = UUID.randomUUID();
    tax = new Taxes(
        id,
        "ICMS",
        17.0D
    );
    taxesPostRequestBody = new TaxesPostRequestBody(
        "ICMS",
        17.0D
    );
    taxesPutRequestBody = new TaxesPutRequestBody(
        id,
        "ISS",
        3.0D
    );

  }

  @Test
  void listAll_ReturnAllTaxes_WhenSuccessful() {
    when(taxesRepository.findAll()).thenReturn(List.of(tax));
    List<Taxes> taxes = taxesService.listAll();
    Assertions.assertTrue(taxesService.listAll().contains(tax));
  }

  @Test
  void listAll_ReturnEmptyList_WhenTaxesNotFound() {
    when(taxesRepository.findAll()).thenReturn(Collections.emptyList());
    List<Taxes> taxes = taxesService.listAll();

    Assertions.assertTrue(taxes.isEmpty());

  }

  @Test
  void findById_ReturnTaxes_WhenSuccessful() {
    when(taxesRepository.findById(tax.getId())).thenReturn(Optional.of(tax));
    Taxes taxes = taxesService.findByIdOrThrowBadRequestException(tax.getId());

    Assertions.assertEquals(tax, taxes);
  }

  @Test
  void findById_ThrowBadRequestException_WhenTaxesNotFound() {
    when(taxesRepository.findById(tax.getId())).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class,
        () -> taxesService.findByIdOrThrowBadRequestException(tax.getId()));
  }

  @Test
  void save_ReturnTaxes_WhenSuccessful() {
    when(taxesRepository.save(any(Taxes.class))).thenReturn(tax);
    Taxes taxes = taxesService.save(taxesPostRequestBody);
    Assertions.assertEquals(tax, taxes);
    verify(taxesRepository).save(any(Taxes.class));
    verifyNoMoreInteractions(taxesRepository);
  }

  @Test
  void save_ThrowBadRequestException_WhenAnyFieldIsBlankOrNull() {
    taxesPostRequestBody.setName("  ");
    taxesPostRequestBody.setAliquot(0.0D);
    Assertions.assertThrows(BadRequestException.class,
        () -> taxesService.save(taxesPostRequestBody));
    verifyNoInteractions(taxesRepository);
  }

  @Test
  void delete_DeletesTaxes_WhenSuccessful() {
    when(taxesRepository.findById(tax.getId())).thenReturn(Optional.of(tax));
    Assertions.assertDoesNotThrow(() -> taxesService.delete(tax.getId()));
    verify(taxesRepository).deleteById(tax.getId());
  }

  @Test
  void delete_ThrowBadExceptionError_WhenTaxesNotFound() {
    when(taxesRepository.findById(tax.getId())).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class, () -> taxesService.delete(tax.getId()));
    verify(taxesRepository, never()).deleteById(tax.getId());
  }

  @Test
  void replace_ReplaceTaxes_WhenSuccessful() {
    when(taxesRepository.findById(taxesPutRequestBody.getId())).thenReturn(Optional.of(tax));
    Assertions.assertDoesNotThrow(() -> taxesService.replace(taxesPutRequestBody));
    verify(taxesRepository).save(any(Taxes.class));
  }

  @Test
  void replace_ThrowBadExceptionError_WhenTaxesNotFound() {
    when(taxesRepository.findById(taxesPutRequestBody.getId())).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class,
        () -> taxesService.replace(taxesPutRequestBody));
    verify(taxesRepository, never()).save(any(Taxes.class));
  }

  @Test
  void replace_ThrowBadExceptionError_WhenAnyFieldIsBlankOrNull() {
    taxesPutRequestBody.setName("  ");
    taxesPutRequestBody.setAliquot(0.0D);
    Assertions.assertThrows(BadRequestException.class,
        () -> taxesService.replace(taxesPutRequestBody));
  }

}
