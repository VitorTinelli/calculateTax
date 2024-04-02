package onboardingMarcos.tinelli.controller;

import onboardingMarcos.tinelli.dto.SelicDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/selic")
public class SelicController {

  @GetMapping
  public Double getSelicPerMonth() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<SelicDTO> resp = restTemplate.getForEntity(
        "https://brasilapi.com.br/api/taxas/v1/SELIC", SelicDTO.class);
    return Double.parseDouble(resp.getBody().getValor()) / 12;
  }

}
