package vitor.tinelli.requests;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class NfePutRequestBody {

  @NotNull
  private UUID id;

  @NotNull
  private Long number;

  @NotNull
  private LocalDate date;

  @NotNull
  private Double value;
}
