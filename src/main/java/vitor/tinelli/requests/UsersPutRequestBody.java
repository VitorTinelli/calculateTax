package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class UsersPutRequestBody {
  @NotNull
  private UUID id;

  @NotBlank
  private String name;

  @NotNull
  private Long cpf;

  @NotBlank
  private String password;

  @NotBlank
  private String authorities;

}
