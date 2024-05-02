package onboardingMarcos.tinelli.controller;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxCalculation;
import onboardingMarcos.tinelli.requests.DateRequestBody;
import onboardingMarcos.tinelli.service.TaxCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxCalculation")
public class TaxCalculationController {

  private final TaxCalculationService taxCalculationService;

  public TaxCalculationController(TaxCalculationService taxCalculationService) {
    this.taxCalculationService = taxCalculationService;
  }

  @GetMapping("")
  public ResponseEntity<List<TaxCalculation>> ListAll() {
    return ResponseEntity.ok(taxCalculationService.ListAll());
  }

  @PostMapping("/post")
  public ResponseEntity<List<TaxCalculation>> postByDatePeriod(
      @RequestBody DateRequestBody dateGapRequestBody) {
    return ResponseEntity.ok(taxCalculationService.postByDatePeriod(dateGapRequestBody.getDate()));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    taxCalculationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
