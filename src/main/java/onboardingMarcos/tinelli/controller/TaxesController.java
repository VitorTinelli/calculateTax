package onboardingMarcos.tinelli.controller;

import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.requests.TaxesPostRequestBody;
import onboardingMarcos.tinelli.requests.TaxesPutRequestBody;
import onboardingMarcos.tinelli.service.TaxesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/taxes")
@RestController
public class TaxesController {

  private final TaxesService taxesService;

  public TaxesController(TaxesService taxesService) {
    this.taxesService = taxesService;
  }

  @GetMapping
  public ResponseEntity<List<Taxes>> listAll() {
    return ResponseEntity.ok(taxesService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Taxes> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(taxesService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<Taxes> save(@RequestBody TaxesPostRequestBody taxesPostRequestBody) {
    return ResponseEntity.ok(taxesService.save(taxesPostRequestBody));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    taxesService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody TaxesPutRequestBody taxesPutRequestBody) {
    taxesService.replace(taxesPutRequestBody);
    return ResponseEntity.noContent().build();
  }


}
