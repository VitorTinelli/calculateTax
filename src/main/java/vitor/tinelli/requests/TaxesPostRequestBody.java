package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaxesPostRequestBody {

  @NotBlank
  private String name;

  @NotNull
  private double aliquot;

}
