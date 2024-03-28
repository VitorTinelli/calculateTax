package onboardingMarcos.tinelli.controller;


import java.util.List;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping
  public ResponseEntity<List<Users>> listAll() {
    return ResponseEntity.ok(userService.listAll());

  }


}
