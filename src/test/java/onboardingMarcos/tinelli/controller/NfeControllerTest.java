package onboardingMarcos.tinelli.controller;


import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.DateGapRequestBody;
import onboardingMarcos.tinelli.requests.NfePostRequestBody;
import onboardingMarcos.tinelli.requests.NfePutRequestBody;
import onboardingMarcos.tinelli.service.NfeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NfeControllerTest {

  Nfe nfe;
  NfePostRequestBody nfePostRequestBody;
  NfePutRequestBody nfePutRequestBody;
  DateGapRequestBody dateGapRequestBody;

  @InjectMocks
  private NfeController nfeController;

  @Mock
  private NfeService nfeService;

  @BeforeEach
  void setUp() {
    UUID id = UUID.randomUUID();
    nfe = new Nfe(id, 12345678910L, LocalDate.now(), 198.00D);
    nfePostRequestBody = new NfePostRequestBody(
        12345678910L,
        LocalDate.now(),
        198.00D
    );
    nfePutRequestBody = new NfePutRequestBody(
        id,
        12345678911L,
        LocalDate.now(),
        198.00D
    );
    dateGapRequestBody = new DateGapRequestBody(
        LocalDate.now(),
        LocalDate.now()
    );

  }

  @Test
  @DisplayName("listAll returns list of nfe when successful")
  void listAll_ReturnListOfNfe_WhenSuccessful() {
    when(nfeService.listAll()).thenReturn(List.of(nfe));

    Assertions.assertEquals(nfeController.listAll().getBody(), List.of(nfe));
    verify(nfeService).listAll();
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("listAll returns empty list when any nfe exists")
  void listAll_ReturnEmptyList_WhenNfeNotExist() {
    when(nfeService.listAll()).thenReturn(Collections.emptyList());

    Assertions.assertEquals(nfeController.listAll().getBody(), Collections.emptyList());
    verify(nfeService).listAll();
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("findByNumber returns nfe when successful")
  void findByNumber_ReturnNfe_WhenSuccessful() {
    when(nfeService.findByNumberOrThrowBadRequestException(
        any(nfe.getNumber().getClass()))).thenReturn(nfe);

    Assertions.assertEquals(nfeController.findByNumber(nfe.getNumber()).getBody(), nfe);
    verify(nfeService).findByNumberOrThrowBadRequestException(any(nfe.getNumber().getClass()));
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Find by Number Throws Bad exception error when NFE not exist")
  void findByNumber_TrowsBadRequestException_WhenNfeNotExist() {
    when(nfeService.findByNumberOrThrowBadRequestException(
        any(nfe.getNumber().getClass()))).thenThrow(
        new BadRequestException("Nfe not found"));

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeController.findByNumber(nfe.getNumber()));
    verify(nfeService).findByNumberOrThrowBadRequestException(any(nfe.getNumber().getClass()));
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("findById returns nfe when successful")
  void findById_ReturnNfe_WhenSuccessful() {
    when(nfeService.findByIdOrThrowBadRequestException(any(UUID.class))).thenReturn(nfe);

    Assertions.assertEquals(nfeController.findById(nfe.getId()).getBody(), nfe);
    verify(nfeService).findByIdOrThrowBadRequestException(any(UUID.class));
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Find by ID Throws Bad exception error when NFE not exist")
  void findById_TrowsBadRequestException_WhenNfeNotExist() {
    when(nfeService.findByIdOrThrowBadRequestException(any(UUID.class))).thenThrow(
        new BadRequestException("Nfe not found"));

    Assertions.assertThrows(BadRequestException.class, () -> nfeController.findById(nfe.getId()));
    verify(nfeService).findByIdOrThrowBadRequestException(any(UUID.class));
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Find by ID Throws Bad exception error when provided ID not exist")
  void findById_TrowsBadRequestException_WhenIdNotExist() {
    when(nfeService.findByIdOrThrowBadRequestException(any())).thenThrow(
        new BadRequestException("Nfe not found"));

    Assertions.assertThrows(BadRequestException.class, () -> nfeController.findById(any()));
    verify(nfeService).findByIdOrThrowBadRequestException(any());
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Find by ID Throws Bad exception error when provided ID is null")
  void findByID_TrowsBadRequestException_WhenIdIsNull() {
    when(nfeService.findByIdOrThrowBadRequestException(null)).thenThrow(
        new BadRequestException("Nfe not found"));

    Assertions.assertThrows(BadRequestException.class, () -> nfeController.findById(null));
    verify(nfeService).findByIdOrThrowBadRequestException(null);
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("listAllByTimeGap returns list of nfe when successful")
  void listAllByTimeGap_ReturnListOfNfe_WhenSuccessful() {
    when(nfeService.findByTimeGap(any(LocalDate.class), any(LocalDate.class))).thenReturn(
        List.of(nfe));

    Assertions.assertTrue(
        nfeController.listAllByTimeGap(dateGapRequestBody).getBody().contains(nfe));
    verify(nfeService).findByTimeGap(any(LocalDate.class), any(LocalDate.class));
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("listAllByTimeGap returns empty list when no nfe found")
  void listAllByTimeGap_ReturnEmptyList_WhenNoNfeFound() {
    when(nfeService.findByTimeGap(any(LocalDate.class), any(LocalDate.class))).thenReturn(
        Collections.emptyList());

    Assertions.assertTrue(
        nfeController.listAllByTimeGap(dateGapRequestBody).getBody().isEmpty());
    verify(nfeService).findByTimeGap(any(LocalDate.class), any(LocalDate.class));
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Save returns nfe when successful")
  void post_ReturnNfe_WhenSuccessful() {
    when(nfeService.save(any(NfePostRequestBody.class))).thenReturn(nfe);

    Assertions.assertEquals(nfeController.save(nfePostRequestBody).getBody(), nfe);
    verify(nfeService).save(any(NfePostRequestBody.class));
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Save throws BadRequestException when any field is null")
  void post_ThrowsBadRequestException_AnyFieldIsNull() {
    nfePostRequestBody.setNumber(null);
    nfePostRequestBody.setDate(null);
    nfePostRequestBody.setValue(null);
    when(nfeService.save(nfePostRequestBody)).thenThrow(
        new BadRequestException("Nfe fields cannot be null"));

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeController.save(nfePostRequestBody));
    verify(nfeService).save(nfePostRequestBody);
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Delete nfe when successful")
  void delete_DeleteNfe_WhenSuccessful() {
    doNothing().when(nfeService).delete(nfe.getId());

    Assertions.assertDoesNotThrow(() -> nfeController.delete(nfe.getId()));
    Assertions.assertEquals(Collections.emptyList(), nfeService.listAll());
    verify(nfeService).delete(nfe.getId());
  }

  @Test
  @DisplayName("Delete throws BadRequestException when nfe not exist")
  void delete_ThrowsBadRequestException_WhenNfeNotExist() {
    doThrow(new BadRequestException("Nfe not found")).when(nfeService).delete(nfe.getId());

    Assertions.assertThrows(BadRequestException.class, () -> nfeController.delete(nfe.getId()));
    verify(nfeService).delete(nfe.getId());
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Delete throws BadRequestException when nfe id is null")
  void delete_ThrowsBadRequestException_WhenNfeIdIsNull() {
    doThrow(new BadRequestException("Nfe not found")).when(nfeService).delete(null);

    Assertions.assertThrows(BadRequestException.class, () -> nfeController.delete(null));
    verify(nfeService).delete(null);
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Replace nfe when successful")
  void put_ReplaceNfe_WhenSuccessful() {
    doNothing().when(nfeService).replace(nfePutRequestBody);

    Assertions.assertDoesNotThrow(() -> nfeController.replace(nfePutRequestBody));
    verify(nfeService).replace(nfePutRequestBody);
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Replace throws BadRequestException when nfe not exist")
  void put_ThrowsBadRequestException_WhenNfeNotExist() {
    doThrow(new BadRequestException("Nfe not found")).when(nfeService).replace(nfePutRequestBody);

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeController.replace(nfePutRequestBody));
    verify(nfeService).replace(nfePutRequestBody);
    verifyNoMoreInteractions(nfeService);
  }

  @Test
  @DisplayName("Replace throws BadRequestException when any nfe field is null")
  void put_ThrowsBadRequestException_WhenAnyFieldIsBlankOrNull() {
    nfePutRequestBody.setId(null);
    nfePutRequestBody.setNumber(null);
    nfePutRequestBody.setDate(null);
    nfePutRequestBody.setValue(null);
    doThrow(new BadRequestException("Nfe fields cannot be null")).when(nfeService)
        .replace(nfePutRequestBody);

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeController.replace(nfePutRequestBody));
    verify(nfeService).replace(nfePutRequestBody);
    verifyNoMoreInteractions(nfeService);
  }


}
