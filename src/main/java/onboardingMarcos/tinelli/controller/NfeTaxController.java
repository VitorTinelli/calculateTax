package onboardingMarcos.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import onboardingMarcos.tinelli.domain.NfeTax;
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
  public ResponseEntity<List<NfeTax>> getByNfeYearAndMonth(@RequestBody @Valid
  NfeTaxYearMonthRequestBody nfeTaxYearMonthRequestBody) {
    return nfeTaxService.getByNfeMonthAndYear(nfeTaxYearMonthRequestBody);
  }

  @PostMapping("/post-all")
  public ResponseEntity<List<NfeTax>> postAllNfeTax() {
    return nfeTaxService.postEveryNfeWithoutTax();
  }

}
