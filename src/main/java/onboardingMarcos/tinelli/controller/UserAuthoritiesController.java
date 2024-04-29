package onboardingMarcos.tinelli.controller;


import java.util.List;
import onboardingMarcos.tinelli.domain.UserAuthorities;
import onboardingMarcos.tinelli.service.UserAuthoritiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-authorities")
public class UserAuthoritiesController {

  private final UserAuthoritiesService userAuthoritiesService;

  public UserAuthoritiesController(UserAuthoritiesService userAuthoritiesService) {
    this.userAuthoritiesService = userAuthoritiesService;
  }

  @GetMapping("")
  public List<UserAuthorities> listAll() {
    return userAuthoritiesService.listAll();
  }

  @PostMapping("/save/{authorities}")
  public ResponseEntity<UserAuthorities> save(@PathVariable String authorities) {
    return ResponseEntity.ok(userAuthoritiesService.save(authorities.toLowerCase()));
  }

  @DeleteMapping("/delete/{authorities}")
  public ResponseEntity<Void> delete(@PathVariable String authorities) {
    userAuthoritiesService.delete(authorities);
    return ResponseEntity.noContent().build();
  }

}
