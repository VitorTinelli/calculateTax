package onboardingMarcos.tinelli.controller;


import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.requests.UserAuthoritiesRequestBody;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
import onboardingMarcos.tinelli.requests.UserPutRequestBody;
import onboardingMarcos.tinelli.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {

  private final UserService userService;

  public UsersController(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<Users>> listAll() {
    return ResponseEntity.ok(userService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Users> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<Users> save(@RequestBody UserPostRequestBody user) {
    return ResponseEntity.ok(userService.save(user));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody UserPutRequestBody userPutRequestBody) {
    userService.replace(userPutRequestBody);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/authorities")
  public ResponseEntity<List<Users>> replaceAuthorities(
      @RequestBody UserAuthoritiesRequestBody userAuthoritiesRequestBody) {
    return ResponseEntity.ok(userService.replaceUsersAuthorities(userAuthoritiesRequestBody));
  }
}
