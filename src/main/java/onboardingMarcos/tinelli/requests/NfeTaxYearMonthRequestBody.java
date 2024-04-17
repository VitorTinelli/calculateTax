package onboardingMarcos.tinelli.requests;

import lombok.Data;

@Data
public class NfeTaxYearMonthRequestBody {

  private String month;
  private Long year;
}
