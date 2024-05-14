package onboardingMarcos.tinelli.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class NfeServiceTest {

  Nfe nfe;
  NfePostRequestBody nfePostRequestBody;
  NfePutRequestBody nfePutRequestBody;
  Page<Nfe> nfePage;

  @InjectMocks
  private NfeService nfeService;

  @Mock
  private NfeRepository nfeRepository;

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
    nfePage = new PageImpl<>(List.of(nfe));
  }

  @Test
  @DisplayName("List all returns a NFE list when successful")
  void listAll_ReturnListOfNfe_WhenSuccessful() {
    when(nfeRepository.findAll(any(Pageable.class))).thenReturn(nfePage);
    Page<Nfe> savedNfe = nfeService.listAll(Pageable.unpaged());

    Assertions.assertEquals(savedNfe, nfePage);
    verify(nfeRepository).findAll(any(Pageable.class));
  }

  @Test
  @DisplayName("List all returns an empty list when no NFE found")
  void listAll_ReturnEmptyList_WhenNfeNotFound() {
    when(nfeRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
    Page<Nfe> savedNfe = nfeService.listAll(Pageable.unpaged());

    Assertions.assertTrue(savedNfe.isEmpty());
    verify(nfeRepository).findAll(any(Pageable.class));
  }

  @Test
  @DisplayName("Find by id returns a NFE when successful")
  void findById_ReturnNfe_WhenSuccessful() {
    when(nfeRepository.findById(nfe.getId())).thenReturn(Optional.of(nfe));
    Nfe savedNfe = nfeService.findByIdOrThrowBadRequestException(nfe.getId());

    Assertions.assertEquals(savedNfe, nfe);
    verify(nfeRepository).findById(nfe.getId());
  }

  @Test
  @DisplayName("Find by id throws BadRequestException when no NFE found")
  void findById_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeRepository.findById(nfe.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeService.findByIdOrThrowBadRequestException(nfe.getId()));
    verify(nfeRepository).findById(nfe.getId());
  }

  @Test
  @DisplayName("Find by date period returns a NFE list when successful")
  void findByDatePeriod_ReturnListOfNfe_WhenSuccessful() {
    when(nfeRepository.findByDateBetween(LocalDate.now(), LocalDate.now())).thenReturn(
        List.of(nfe));
    List<Nfe> savedNfe = nfeService.findByTimePeriod(LocalDate.now(), LocalDate.now());

    Assertions.assertEquals(savedNfe, List.of(nfe));
    verify(nfeRepository).findByDateBetween(LocalDate.now(), LocalDate.now());
  }

  @Test
  @DisplayName("Find by NFE number returns a NFE when successful")
  void findByNfeNumber_ReturnNfe_WhenSuccessful() {
    when(nfeRepository.findByNumber(nfe.getNumber())).thenReturn(Optional.of(nfe));
    Nfe savedNfe = nfeService.findByNumberOrThrowBadRequestException(nfe.getNumber());

    Assertions.assertEquals(savedNfe, nfe);
    verify(nfeRepository).findByNumber(nfe.getNumber());
  }

  @Test
  @DisplayName("Find by NFE number throws BadRequestException when no NFE found")
  void findByNfeNumber_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeRepository.findByNumber(nfe.getNumber())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeService.findByNumberOrThrowBadRequestException(nfe.getNumber()));
    verify(nfeRepository).findByNumber(nfe.getNumber());
  }

  @Test
  @DisplayName("Save returns and save a NFE when successful")
  void save_ReturnNfe_WhenSuccessful() {
    when(nfeRepository.save(any(Nfe.class))).thenReturn(nfe);
    Nfe savedNfe = nfeService.save(nfePostRequestBody);

    Assertions.assertEquals(savedNfe, nfe);
    verify(nfeRepository).save(any(Nfe.class));
  }

  @Test
  @DisplayName("Save throws BadRequestException when NFE number is already registered")
  void save_ThrowBadRequestException_WhenNfeNumberAlreadyRegistered() {
    Nfe nfe2 = new Nfe(
        UUID.randomUUID(),
        12345678911L,
        LocalDate.now(),
        198.00D
    );
    when(nfeRepository.findByNumber(nfePostRequestBody.getNumber())).thenReturn(Optional.of(nfe2));
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeService.save(nfePostRequestBody));
    verify(nfeRepository).findByNumber(nfePostRequestBody.getNumber());
  }

  @Test
  @DisplayName("Save throws BadRequestException when any verification fail")
  void save_ThrowBadRequestException_WhenAnyVerificationFail() {
    Assertions.assertAll(
        () -> {
          nfePostRequestBody.setValue(-1.2D);
          Assertions.assertThrows(BadRequestException.class,
              () -> nfeService.save(nfePostRequestBody));
        },
        () -> {
          nfePostRequestBody.setValue(2.2D);
          nfePostRequestBody.setDate(LocalDate.of(2050, 1, 1));
          Assertions.assertThrows(BadRequestException.class,
              () -> nfeService.save(nfePostRequestBody));
        }
    );
  }

  @Test
  @DisplayName("Delete deletes a NFE when successful")
  void delete_DeletesNfe_WhenSuccessful() {
    when(nfeRepository.findById(nfe.getId())).thenReturn(Optional.of(nfe));
    Assertions.assertDoesNotThrow(() -> nfeService.delete(nfe.getId()));
    verify(nfeRepository).delete(nfe);
  }

  @Test
  @DisplayName("Delete throws BadRequestException when no NFE found")
  void delete_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeRepository.findById(nfe.getId())).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class, () -> nfeService.delete(nfe.getId()));
    verify(nfeRepository).findById(nfe.getId());
  }

  @Test
  @DisplayName("Replace replaces a NFE when successful")
  void replace_ReplaceNfe_WhenSuccessful() {
    when(nfeRepository.findById(nfePutRequestBody.getId())).thenReturn(Optional.of(nfe));
    Assertions.assertDoesNotThrow(() -> nfeService.replace(nfePutRequestBody));
    verify(nfeRepository).save(any(Nfe.class));
  }

  @Test
  @DisplayName("Replace throws BadRequestException when no NFE found")
  void replace_ThrowBadRequestException_WhenNfeNotFound() {
    when(nfeRepository.findById(nfePutRequestBody.getId())).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class,
        () -> nfeService.replace(nfePutRequestBody));
    verify(nfeRepository).findById(nfePutRequestBody.getId());
  }

  @Test
  @DisplayName("Replace throws BadRequestException when any verification fail")
  void replace_ThrowBadRequestException_WhenAnyVerificationFail() {
    Assertions.assertAll(
        () -> {
          nfePutRequestBody.setValue(-1.2D);
          Assertions.assertThrows(BadRequestException.class,
              () -> nfeService.replace(nfePutRequestBody));
        },
        () -> {
          nfePutRequestBody.setValue(2.2D);
          nfePutRequestBody.setDate(LocalDate.of(2050, 1, 1));
          Assertions.assertThrows(BadRequestException.class,
              () -> nfeService.replace(nfePutRequestBody));
        }
    );
  }

  @Test
  @DisplayName("Replace throws BadRequestException when NFE number is already registered")
  void replace_ThrowBadRequestException_WhenNewNfeNumberAlreadyRegistered() {
    Nfe nfe2 = new Nfe(
        UUID.randomUUID(),
        12345678911L,
        LocalDate.now(),
        198.00D
    );
    when(nfeRepository.findById(nfePutRequestBody.getId())).thenReturn(Optional.of(nfe));
    when(nfeRepository.findByNumber(nfePutRequestBody.getNumber())).thenReturn(Optional.of(nfe2));

    Assertions.assertThrows(BadRequestException.class,
        () -> nfeService.replace(nfePutRequestBody));
    verify(nfeRepository).findByNumber(nfePutRequestBody.getNumber());
  }
}
