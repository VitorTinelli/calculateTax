package onboardingMarcos.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.requests.DateGapRequestBody;
import onboardingMarcos.tinelli.requests.NfeTaxYearMonthRequestBody;
import onboardingMarcos.tinelli.service.NfeTaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/gerente")
public class NfeTaxController {

  private final NfeTaxService nfeTaxService;

  public NfeTaxController(NfeTaxService nfeTaxService) {
    this.nfeTaxService = nfeTaxService;
  }

  @GetMapping("/list-all")
  public ResponseEntity<List<NfeTax>> listAll() {
    return nfeTaxService.listAll();
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<List<NfeTax>> getByNfeId(@PathVariable String uuid) {
    return nfeTaxService.getByNfeId(uuid);
  }

  @GetMapping("/list-all/{year}")
  public ResponseEntity<List<NfeTax>> getByNfeYear(@PathVariable Long year) {
    return nfeTaxService.getByNfeYear(year);
  }

  @GetMapping("/list-month")
  public ResponseEntity<List<NfeTax>> getByNfeYearAndMonth(
      @RequestBody @Valid NfeTaxYearMonthRequestBody nfeTaxYearMonthRequestBody) {
    return nfeTaxService.getByNfeMonthAndYear(nfeTaxYearMonthRequestBody);
  }

  @PostMapping("/post-all")
  public ResponseEntity<List<NfeTax>> postAllNfeTax() {
    return nfeTaxService.postEveryNfeWithoutTax();
  }

  @PostMapping("/post-all/date")
  public ResponseEntity<List<NfeTax>> postAllNfeTaxByDateGap(
      @RequestBody DateGapRequestBody dateGapRequestBody) {
    return nfeTaxService.postEveryNfeWithoutTaxByDateGap(dateGapRequestBody.getStartDate(),
        dateGapRequestBody.getEndDate());
  }

  @DeleteMapping("/delete-all/{id}")
  public ResponseEntity<Void> deleteAllByNfeID(@PathVariable UUID id) {
    nfeTaxService.deleteAllByNfeID(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteByNfeTaxId(@PathVariable UUID id) {
    nfeTaxService.deleteByNfeTaxIdOrThrowBadRequestException(id);
    return ResponseEntity.noContent().build();
  }

}
