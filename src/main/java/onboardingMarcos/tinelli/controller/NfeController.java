package onboardingMarcos.tinelli.controller;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.requests.DateGapRequestBody;
import onboardingMarcos.tinelli.requests.NfePostRequestBody;
import onboardingMarcos.tinelli.requests.NfePutRequestBody;
import onboardingMarcos.tinelli.service.NfeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nfe")
public class NfeController {

  private final NfeService nfeService;

  public NfeController(final NfeService nfeService) {
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

  @GetMapping("/number/{number}")
  public ResponseEntity<Nfe> findByNumber(@PathVariable Long number) {
    return ResponseEntity.ok(nfeService.findByNumberOrThrowBadRequestException(number));
  }

  @GetMapping("/date")
  public ResponseEntity<List<Nfe>> listAllByTimeGap(
      @RequestBody DateGapRequestBody dateGapRequestBody) {
    return ResponseEntity.ok(nfeService.findByTimeGap(dateGapRequestBody.getStartDate(),
        dateGapRequestBody.getEndDate()));
  }

  @PostMapping
  public ResponseEntity<Nfe> save(@RequestBody NfePostRequestBody nfePostRequestBody) {
    return ResponseEntity.ok(nfeService.save(nfePostRequestBody));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    nfeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody NfePutRequestBody nfePutRequestBody) {
    nfeService.replace(nfePutRequestBody);
    return ResponseEntity.noContent().build();
  }
}
