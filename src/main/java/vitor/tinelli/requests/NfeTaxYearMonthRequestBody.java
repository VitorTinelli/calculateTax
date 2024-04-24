package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NfeTaxYearMonthRequestBody {

  @NotBlank
  private String month;
  @NotNull
  private Integer year;

}
