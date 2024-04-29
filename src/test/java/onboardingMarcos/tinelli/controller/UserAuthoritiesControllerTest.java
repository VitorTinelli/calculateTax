package onboardingMarcos.tinelli.controller;


import static org.mockito.Mockito.*;
import java.util.List;
import onboardingMarcos.tinelli.domain.UserAuthorities;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.service.UserAuthoritiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAuthoritiesControllerTest {

  UserAuthorities auth;

  @InjectMocks
  private UserAuthoritiesController userAuthoritiesController;

  @Mock
  private UserAuthoritiesService userAuthoritiesService;

  @BeforeEach
  void setUp() {
    auth = new UserAuthorities("gerente");
  }

  @Test
  void listAll_ReturnsListOfUserAuthorities_WhenSuccessful() {
    when(userAuthoritiesService.listAll()).thenReturn(List.of(auth));

    Assertions.assertTrue(userAuthoritiesController.listAll().contains(auth));
    verify(userAuthoritiesService).listAll();
  }

  @Test
  void listAll_ReturnsEmptyListOfUserAuthorities_WhenUserAuthoritiesNotFound() {
    when(userAuthoritiesService.listAll()).thenReturn(List.of());

    Assertions.assertTrue(userAuthoritiesController.listAll().isEmpty());
    verify(userAuthoritiesService).listAll();
  }

  @Test
  void save_savesUserAuthorities_WhenSuccessful() {
    when(userAuthoritiesService.save("gerente")).thenReturn(auth);

    Assertions.assertEquals(auth, userAuthoritiesController.save("gerente").getBody());
    verify(userAuthoritiesService).save("gerente");
  }

  @Test
  void save_throwBadRequestException_WhenUserAuthoritiesAlreadyExists() {
    when(userAuthoritiesService.save("gerente")).thenThrow(
        new BadRequestException("User Authorities already exists"));

    Assertions.assertThrows(BadRequestException.class,
        () -> userAuthoritiesController.save("gerente"));
    Assertions.assertTrue(userAuthoritiesController.listAll().isEmpty());
    verify(userAuthoritiesService).save("gerente");
  }

  @Test
  void delete_deletesUserAuthorities_WhenSuccessful() {
    Assertions.assertDoesNotThrow(() -> userAuthoritiesController.delete("gerente"));
    verify(userAuthoritiesService).delete("gerente");
  }

  @Test
  void delete_throwBadRequestException_WhenUserAuthoritiesNotFound() {
    doThrow(new BadRequestException(
        "User Authorities not Found, Please verify the provided Authorities"))
        .when(userAuthoritiesService).delete("gerente");

    Assertions.assertThrows(BadRequestException.class,
        () -> userAuthoritiesController.delete("gerente"));
    verify(userAuthoritiesService).delete("gerente");
  }

}
