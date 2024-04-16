package vitor.tinelli.controller;

import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vitor.tinelli.DTO.Selic;

@RestController
@RequestMapping("/selic")
public class SelicController {

  @GetMapping
  public Double getSelicAliquotPerMonth() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Selic> response = restTemplate.getForEntity(
        "https://brasilapi.com.br/api/taxas/v1/SELIC", Selic.class);
    return Double.parseDouble(Objects.requireNonNull(response.getBody()).getValor()) / 12;
  }

}
