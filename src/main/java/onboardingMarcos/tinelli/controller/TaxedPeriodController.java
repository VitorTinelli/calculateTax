package onboardingMarcos.tinelli.controller;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxedPeriod;
import onboardingMarcos.tinelli.requests.DateRequestBody;
import onboardingMarcos.tinelli.service.TaxedPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxCalculation")
public class TaxedPeriodController {

  private final TaxedPeriodService taxedPeriodService;

  public TaxedPeriodController(final TaxedPeriodService taxedPeriodService) {
    this.taxedPeriodService = taxedPeriodService;
  }

  @GetMapping("")
  public ResponseEntity<List<TaxedPeriod>> listAll() {
    return ResponseEntity.ok(taxedPeriodService.listAll());
  }

  @GetMapping("/find/month")
  public ResponseEntity<List<TaxedPeriod>> findByMonth(
      @RequestBody DateRequestBody dateRequestBody) {
    return ResponseEntity.ok(taxedPeriodService.findByMonth(dateRequestBody.getDate()));
  }

  @PostMapping("/post")
  public ResponseEntity<List<TaxedPeriod>> postByDatePeriod(
      @RequestBody DateRequestBody dateGapRequestBody) {
    return ResponseEntity.ok(taxedPeriodService.postByDatePeriod(dateGapRequestBody.getDate()));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    taxedPeriodService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/delete-all/month")
  public ResponseEntity<Void> deleteAllByMonth(@RequestBody DateRequestBody dateRequestBody) {
    taxedPeriodService.deleteAllByMonth(dateRequestBody.getDate());
    return ResponseEntity.noContent().build();
  }
}
