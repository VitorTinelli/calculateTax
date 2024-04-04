package onboardingMarcos.tinelli.controller;

import java.util.List;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.service.NfeTaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/gerente")
public class NfeTaxController {

  private final NfeTaxService nfeTaxService;

  public NfeTaxController(NfeTaxService nfeTaxService) {
    this.nfeTaxService = nfeTaxService;
  }

  @GetMapping("/monthly-nfe-tax")
  public ResponseEntity<List<NfeTax>> getMonthlyNfeTax() {
    return nfeTaxService.postSeparatedByYearAndMonth();
  }

}
