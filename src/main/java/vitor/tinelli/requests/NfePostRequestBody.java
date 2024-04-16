package vitor.tinelli.requests;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class NfePostRequestBody {

  @NotNull
  private Long number;

  @NotNull
  private LocalDate date;

  @NotNull
  private Double value;
}
