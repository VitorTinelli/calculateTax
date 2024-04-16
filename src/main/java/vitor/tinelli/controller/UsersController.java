package vitor.tinelli.controller;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.Users;
import vitor.tinelli.requests.UsersPostRequestBody;
import vitor.tinelli.requests.UsersPutRequestBody;
import vitor.tinelli.service.UsersService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

  private final UsersService usersService;

  @GetMapping
  public ResponseEntity<List<Users>> listAll() {
    return ResponseEntity.ok(usersService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Users> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(usersService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<Users> save(@RequestBody @Valid UsersPostRequestBody user) {
    return ResponseEntity.ok(usersService.save(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    usersService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody @Valid UsersPutRequestBody user) {
    usersService.replace(user);
    return ResponseEntity.noContent().build();
  }

}
