package onboardingMarcos.tinelli.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Collections;
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

@ExtendWith(MockitoExtension.class)
class NfeServiceTest {

  Nfe nfe;
  NfePostRequestBody nfePostRequestBody;
  NfePutRequestBody nfePutRequestBody;

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
  }

  @Test
  @DisplayName("List all returns a NFE list when successful")
  void listAll_ReturnListOfNfe_WhenSuccessful() {
    when(nfeRepository.findAll()).thenReturn(List.of(nfe));
    List<Nfe> savedNfe = nfeService.listAll();

    Assertions.assertEquals(savedNfe, List.of(nfe));
    verify(nfeRepository).findAll();
  }

  @Test
  @DisplayName("List all returns an empty list when no NFE found")
  void listAll_ReturnEmptyList_WhenNfeNotFound() {
    when(nfeRepository.findAll()).thenReturn(Collections.emptyList());
    List<Nfe> savedNfe = nfeService.listAll();

    Assertions.assertTrue(savedNfe.isEmpty());
    verify(nfeRepository).findAll();
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
  @DisplayName("Save returns and save a NFE when successful")
  void save_ReturnNfe_WhenSuccessful() {
    when(nfeRepository.save(any(Nfe.class))).thenReturn(nfe);
    Nfe savedNfe = nfeService.save(nfePostRequestBody);

    Assertions.assertEquals(savedNfe, nfe);
    verify(nfeRepository).save(any(Nfe.class));
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
    verify(nfeRepository).deleteById(nfe.getId());
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
}
