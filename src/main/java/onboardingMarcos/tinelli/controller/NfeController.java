package onboardingMarcos.tinelli.controller;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.requests.NfePostRequestBody;
import onboardingMarcos.tinelli.service.NfeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nfe")
public class NfeController {

  private final NfeService nfeService;

  public NfeController(NfeService nfeService) {
    this.nfeService = nfeService;
  }

  @GetMapping
  public ResponseEntity<List<Nfe>> listAll() {
    return ResponseEntity.ok(nfeService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Nfe> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(nfeService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<Nfe> save(@RequestBody NfePostRequestBody nfePostRequestBody) {
    return ResponseEntity.ok(nfeService.save(nfePostRequestBody));
  }
}
