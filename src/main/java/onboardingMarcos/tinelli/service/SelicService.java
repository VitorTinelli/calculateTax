package onboardingMarcos.tinelli.service;

import onboardingMarcos.tinelli.dto.SelicDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SelicService {

  public Double getSelicPerMonth() {
    RestTemplate restTemplate = new RestTemplate();
    SelicDTO resp = restTemplate.getForObject(
        "https://brasilapi.com.br/api/taxas/v1/SELIC", SelicDTO.class);
    return Double.parseDouble(resp.getValor()) / 12;
  }
}
