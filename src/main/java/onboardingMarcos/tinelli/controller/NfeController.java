package onboardingMarcos.tinelli.controller;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.requests.DatePeriodRequestBody;
import onboardingMarcos.tinelli.requests.NfePostRequestBody;
import onboardingMarcos.tinelli.requests.NfePutRequestBody;
import onboardingMarcos.tinelli.service.NfeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<Page<Nfe>> listAll(Pageable pageable) {
    return ResponseEntity.ok(nfeService.listAll(pageable));
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
  public ResponseEntity<List<Nfe>> listAllInTimePeriod(
      @RequestBody DatePeriodRequestBody datePeriodRequestBody) {
    return ResponseEntity.ok(nfeService.findByTimePeriod(datePeriodRequestBody.getStartDate(),
        datePeriodRequestBody.getEndDate()));
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
