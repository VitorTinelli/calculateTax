package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class TaxesPutRequestBody {

  @NotNull
  private UUID id;

  @NotBlank
  private String name;

  @NotNull
  private double aliquot;

}
