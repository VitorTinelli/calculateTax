package onboardingMarcos.tinelli.controller;


import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.UserAuthoritiesRequestBody;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
import onboardingMarcos.tinelli.requests.UserPutRequestBody;
import onboardingMarcos.tinelli.service.UserService;
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
class UserControllerTest {

  private Users user;
  private UserPostRequestBody userPostRequestBody;
  private UserPutRequestBody userPutRequestBody;

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() {
    UUID id = UUID.randomUUID();
    user = new Users(id, "Marcos", 12345678910L, "12345678910", "senhasecreta", "gerente");
    userPostRequestBody = new UserPostRequestBody(
        "Marcos",
        12345678910L,
        "senhasecreta",
        "gerente"
    );
    userPutRequestBody = new UserPutRequestBody(
        id,
        "Giba",
        12345678911L,
        "senhasecreta1",
        "contador"
    );
  }

  @Test
  @DisplayName("listAll returns list of users when successful")
  void listAll_ReturnsAListOfUsers_WhenSuccessful() {
    when(userService.listAll()).thenReturn(List.of(user));
    ResponseEntity<List<Users>> users = userController.listAll();

    Assertions.assertEquals(users, userController.listAll());
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("listAll returns empty list when any user exists")
  void listAll_ReturnEmptyList_WhenUsersNotExist() {
    when(userService.listAll()).thenReturn(Collections.emptyList());
    ResponseEntity<List<Users>> users = userController.listAll();

    verify(userService).listAll();
    verifyNoMoreInteractions(userService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), users);
  }

  @Test
  @DisplayName("findById returns an user when successful")
  void findById_ReturnAnUser_WhenSuccessful() {
    when(userService.findByIdOrThrowBadRequestException(user.getId())).thenReturn(user);
    ResponseEntity<Users> users = userController.findById(user.getId());

    verify(userService).findByIdOrThrowBadRequestException(user.getId());
    verifyNoMoreInteractions(userService);
    Assertions.assertEquals(ResponseEntity.ok(user), users);
  }

  @Test
  @DisplayName("findById returns an user when successful")
  void findById_ThrowsBadRequestException_WheUserNotExist() {
    when(userService.findByIdOrThrowBadRequestException(user.getId())).thenThrow(
        new BadRequestException("User not Found, Please verify the provided ID"));

    Assertions.assertThrows(BadRequestException.class, () -> userController.findById(user.getId()));
    verify(userService).findByIdOrThrowBadRequestException(user.getId());
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("findById returns an exception when user id is null")
  void findByID_ThrowsBadRequestException_WhenUserIDisNull() {
    when(userService.findByIdOrThrowBadRequestException(null)).thenThrow(
        new BadRequestException("User not Found, Please verify the provided ID"));

    Assertions.assertThrows(BadRequestException.class, () -> userController.findById(null));
    verify(userService).findByIdOrThrowBadRequestException(null);
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("post returns an user when successful")
  void post_ReturnsUser_WhenSuccessful() {
    when(userService.save(any(UserPostRequestBody.class))).thenReturn(user);
    ResponseEntity<Users> users = userController.save(userPostRequestBody);

    Assertions.assertEquals(users, userController.save(userPostRequestBody));
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("post saves user when successful")
  void post_ThrowsBadRequestException_WhenUserCPFAlreadyRegistered() {
    when(userService.save(userPostRequestBody)).thenThrow(
        new BadRequestException("CPF already registered"));

    Assertions.assertThrows(BadRequestException.class,
        () -> userController.save(userPostRequestBody));
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("post saves user when successful")
  void post_ThrowsBadRequestException_WhenAnyFieldIsBlackOrNull() {
    userPostRequestBody.setName(" ");
    userPostRequestBody.setCpf(null);
    userPostRequestBody.setPassword(" ");
    userPostRequestBody.setUserType(" ");

    when(userService.save(userPostRequestBody)).thenThrow(
        new BadRequestException("User fields cannot be blank"));

    Assertions.assertThrows(BadRequestException.class,
        () -> userController.save(userPostRequestBody));
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("delete removes user when successful")
  void delete_RemovesUser_WhenSuccessful() {
    ResponseEntity<Void> deletar = userController.delete(user.getId());

    Assertions.assertEquals(ResponseEntity.noContent().build(), deletar);
    verify(userService).delete(user.getId());
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("delete throws BadRequestException when user not exist")
  void delete_ThrowsBadRequestException_WhenUserNotExist() {
    doThrow(new BadRequestException("User not Found, Please verify the provided ID"))
        .when(userService).delete(user.getId());

    Assertions.assertThrows(BadRequestException.class, () -> userController.delete(user.getId()));
    verify(userService).delete(user.getId());
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("delete throws BadRequestException when user id is null")
  void delete_ThrowsBadRequestException_WhenProvidedIdIsNull() {
    doThrow(new BadRequestException("ID not provided, please provide an ID")).when(userService)
        .delete(null);
    Assertions.assertThrows(BadRequestException.class, () -> userController.delete(null));
    verify(userService).delete(null);
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("put updates user when successful")
  void put_ReplaceBrand_WhenSuccessful() {
    doNothing().when(userService).replace(any(UserPutRequestBody.class));
    ResponseEntity<Void> user = userController.replace(userPutRequestBody);

    Assertions.assertEquals(ResponseEntity.noContent().build(), user);
    verify(userService).replace(any(UserPutRequestBody.class));
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("put throws BadRequestException when CPF is already registered")
  void put_ThrowsBadRequestException_WhenUserCPFAlreadyRegistered() {
    doThrow(new BadRequestException("CPF already registered")).when(userService)
        .replace(userPutRequestBody);

    Assertions.assertThrows(BadRequestException.class,
        () -> userController.replace(userPutRequestBody));
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("put throws BadRequestException when user name is null, empty or blank")
  void put_ThrowsBadRequestException_WhenAnyFieldIsBlackOrNull() {
    userPutRequestBody.setName(" ");
    userPutRequestBody.setCpf(null);
    userPutRequestBody.setPassword(" ");
    userPutRequestBody.setUserType(" ");

    doThrow(new BadRequestException("User fields cannot be blank")).when(userService)
        .replace(userPutRequestBody);

    Assertions.assertThrows(BadRequestException.class,
        () -> userController.replace(userPutRequestBody));
    verifyNoMoreInteractions(userService);
  }

  @Test
  @DisplayName("put UserAuthorities replaces all user authorities when successful")
  void put_ReplaceUserAuthorities_WhenSuccessful() {
    when(userService.replaceUsersAuthorities(any(UserAuthoritiesRequestBody.class))).thenReturn(
        List.of(user));

    ResponseEntity<List<Users>> users = userController.replaceAuthorities(
        new UserAuthoritiesRequestBody(
            "contador", "gerente"));

    Assertions.assertEquals(users, userController.replaceAuthorities(new UserAuthoritiesRequestBody(
        "contador", "gerente")));

  }
}
