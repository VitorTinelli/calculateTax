package onboardingMarcos.tinelli.controller;

import java.util.List;
import onboardingMarcos.tinelli.domain.NfeTax;
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

  @PostMapping("/post-all")
  public ResponseEntity<List<NfeTax>> postAllNfeTax() {
    return nfeTaxService.postEveryNfeWithoutTax();
  }

}
