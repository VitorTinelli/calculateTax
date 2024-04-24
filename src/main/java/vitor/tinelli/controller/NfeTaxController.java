package vitor.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.NfeTax;
import vitor.tinelli.requests.DateGapRequestBody;
import vitor.tinelli.requests.NfeTaxYearMonthRequestBody;
import vitor.tinelli.service.NfeTaxService;

@RestController
@RequestMapping("/gerente")
@RequiredArgsConstructor
public class NfeTaxController {

  private final NfeTaxService nfeTaxService;

  @GetMapping("/list-all")
  public ResponseEntity<List<NfeTax>> listAll() {
    return ResponseEntity.ok(nfeTaxService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<NfeTax>> findByNfe(@PathVariable UUID id) {
    return ResponseEntity.ok(nfeTaxService.listByNfeID(id));
  }

  @GetMapping("/list-month")
  public ResponseEntity<List<NfeTax>> getByNfeYearAndMonth(@RequestBody @Valid
  NfeTaxYearMonthRequestBody nfeTaxYearMonthRequestBody) {
    return nfeTaxService.getByNfeMonthAndYear(nfeTaxYearMonthRequestBody);
  }

  @GetMapping("/list-all/{year}")
  public ResponseEntity<List<NfeTax>> getByNfeYear(@PathVariable Integer year) {
    return nfeTaxService.getByNfeYear(year);
  }

  @PostMapping("/post-all")
  public ResponseEntity<List<NfeTax>> postAllNfeTax() {
    return ResponseEntity.ok(nfeTaxService.postEveryNfeWithoutTax());
  }

  @PostMapping("/post-all/date")
  public ResponseEntity<List<NfeTax>> postAllNfeTaxByDate(
      @RequestBody @Valid DateGapRequestBody dateGapRequestBody) {
    return ResponseEntity.ok(
        nfeTaxService.postEveryNfeWithoutTaxByDate(dateGapRequestBody));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    nfeTaxService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/list-all/{id}")
  public ResponseEntity<Void> deleteByNfeId(@PathVariable UUID id) {
    nfeTaxService.deleteByNfeId(id);
    return ResponseEntity.noContent().build();
  }

}
