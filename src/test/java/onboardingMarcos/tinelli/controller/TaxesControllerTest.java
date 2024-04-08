package onboardingMarcos.tinelli.controller;

import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.TaxesPostRequestBody;
import onboardingMarcos.tinelli.requests.TaxesPutRequestBody;
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
class TaxesControllerTest {

  Taxes tax;
  TaxesPostRequestBody taxesPostRequestBody;
  TaxesPutRequestBody taxesPutRequestBody;

  @InjectMocks
  private TaxesController taxesController;

  @Mock
  private TaxesService taxesService;

  @BeforeEach
  void setUp() {

    UUID id = UUID.randomUUID();
    tax = new Taxes(id, "Selic", 10.3);

    taxesPostRequestBody = new TaxesPostRequestBody(
        "Selic",
        10.3
    );

    taxesPutRequestBody = new TaxesPutRequestBody(
        id,
        "ICMS",
        10.3
    );

  }

  @Test
  @DisplayName("listAll returns list of taxes when successful")
  void listAll_ReturnAllTaxes_WhenSuccessful() {
    when(taxesService.listAll()).thenReturn(List.of(tax));
    ResponseEntity<List<Taxes>> taxes = taxesController.listAll();

    verify(taxesService).listAll();
    verifyNoMoreInteractions(taxesService);
    Assertions.assertEquals(ResponseEntity.ok(List.of(tax)), taxes);
  }

  @Test
  @DisplayName("listAll returns empty list when any tax exists")
  void listAll_ReturnEmptyList_WhenTaxesNotExist() {
    when(taxesService.listAll()).thenReturn(Collections.emptyList());
    ResponseEntity<List<Taxes>> taxes = taxesController.listAll();

    verify(taxesService).listAll();
    verifyNoMoreInteractions(taxesService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), taxes);
  }

  @Test
  @DisplayName("findById returns tax when successful")
  void findById_ReturnTax_WhenSuccessful() {
    when(taxesService.findByIdOrThrowBadRequestException(tax.getId())).thenReturn(tax);
    ResponseEntity<Taxes> taxes = taxesController.findById(tax.getId());

    verify(taxesService).findByIdOrThrowBadRequestException(tax.getId());
    verifyNoMoreInteractions(taxesService);
    Assertions.assertEquals(ResponseEntity.ok(tax), taxes);
  }

  @Test
  @DisplayName("findById returns an exception when tax not exist")
  void findByID_ReturnAnException_WhenTaxNotExist() {
    when(taxesService.findByIdOrThrowBadRequestException(tax.getId())).thenThrow(
        new BadRequestException("Tax not found"));
    Assertions.assertThrows(BadRequestException.class, () -> taxesController.findById(tax.getId()));

    verify(taxesService).findByIdOrThrowBadRequestException(tax.getId());
    verifyNoMoreInteractions(taxesService);
  }

  @Test
  @DisplayName("findById returns an exception when tax id is null")
  void post_ReturnTax_WhenSuccessful() {
    when(taxesService.save(taxesPostRequestBody)).thenReturn(tax);
    ResponseEntity<Taxes> taxes = taxesController.save(taxesPostRequestBody);

    verify(taxesService).save(taxesPostRequestBody);
    verifyNoMoreInteractions(taxesService);
    Assertions.assertEquals(taxesPostRequestBody.getName(), taxes.getBody().getName());
    Assertions.assertEquals(ResponseEntity.ok(tax), taxes);
  }

  @Test
  @DisplayName("post saves tax when successful")
  void post_ReturnAnBadRequestException_WhenTaxNameIsBlank() {
    taxesPostRequestBody.setName(" ");
    when(taxesService.save(taxesPostRequestBody)).thenThrow(
        new BadRequestException("Tax name cannot be blank"));

    verifyNoMoreInteractions(taxesService);
    Assertions.assertThrows(BadRequestException.class,
        () -> taxesController.save(taxesPostRequestBody));
    Assertions.assertEquals(Collections.emptyList(), taxesService.listAll());
  }

  @Test
  @DisplayName("post saves tax when successful")
  void post_ThrowsBadRequestException_WhenTaxIsLesserThanZero() {
    taxesPostRequestBody.setAliquot(0D);
    when(taxesService.save(taxesPostRequestBody)).thenThrow(
        new BadRequestException("Tax aliquot less than 0"));

    verifyNoMoreInteractions(taxesService);
    Assertions.assertThrows(BadRequestException.class,
        () -> taxesController.save(taxesPostRequestBody));
    Assertions.assertEquals(Collections.emptyList(), taxesService.listAll());
  }

  @Test
  @DisplayName("delete deletes tax when successful")
  void delete_DeletesTaxes_WhenSuccessful() {
    doNothing().when(taxesService).delete(tax.getId());

    Assertions.assertDoesNotThrow(() -> taxesController.delete(tax.getId()));
    Assertions.assertEquals(Collections.emptyList(), taxesService.listAll());
    verify(taxesService).delete(tax.getId());
  }

  @Test
  @DisplayName("delete throws BadRequestException when tax not exist")
  void delete_ThrowsBadRequestException_WhenTaxNotExist() {
    doThrow(new BadRequestException("Tax not found")).when(taxesService).delete(tax.getId());

    Assertions.assertThrows(BadRequestException.class, () -> taxesController.delete(tax.getId()));
    verify(taxesService).delete(tax.getId());
    verifyNoMoreInteractions(taxesService);
  }

  @Test
  @DisplayName("delete throws BadRequestException when tax id is null")
  void delete_ThrowsBadRequestException_WhenIdIsNull() {
    doThrow(new BadRequestException("Tax not found")).when(taxesService).delete(null);

    Assertions.assertThrows(BadRequestException.class, () -> taxesController.delete(null));
    verify(taxesService).delete(null);
    verifyNoMoreInteractions(taxesService);
  }

  @Test
  @DisplayName("put replaces tax when successful")
  void put_ReplaceTax_WhenSuccessful() {
    doNothing().when(taxesService).replace(taxesPutRequestBody);

    Assertions.assertDoesNotThrow(() -> taxesController.replace(taxesPutRequestBody));
    verify(taxesService).replace(taxesPutRequestBody);
    verifyNoMoreInteractions(taxesService);
  }

  @Test
  @DisplayName("put throws BadRequestException when tax not exist")
  void put_ThrowsError_WhenIdIsnull() {
    taxesPutRequestBody.setId(null);
    doThrow(new BadRequestException("Tax not found")).when(taxesService)
        .replace(taxesPutRequestBody);

    Assertions.assertThrows(BadRequestException.class,
        () -> taxesController.replace(taxesPutRequestBody));
    verify(taxesService).replace(taxesPutRequestBody);
  }

  @Test
  @DisplayName("put throws BadRequestException when tax name is blank")
  void put_ThrowsBadRequestException_WhenTaxNameIsBlank() {
    taxesPutRequestBody.setName(" ");
    doThrow(new BadRequestException("Tax name cannot be blank")).when(taxesService)
        .replace(taxesPutRequestBody);

    Assertions.assertThrows(BadRequestException.class,
        () -> taxesController.replace(taxesPutRequestBody));
    Assertions.assertEquals(Collections.emptyList(), taxesService.listAll());
    verify(taxesService).replace(taxesPutRequestBody);
  }

  @Test
  @DisplayName("put throws BadRequestException when tax aliquot is lesser than 0")
  void put_ThrowsBadRequestException_WhenTaxAliquotIsLesserThanZero() {
    taxesPutRequestBody.setAliquot(0D);
    doThrow(new BadRequestException("Tax aliquot cannot be lesser than 0")).when(taxesService)
        .replace(taxesPutRequestBody);

    Assertions.assertThrows(BadRequestException.class,
        () -> taxesController.replace(taxesPutRequestBody));
    Assertions.assertEquals(Collections.emptyList(), taxesService.listAll());
    verify(taxesService).replace(taxesPutRequestBody);
  }

}