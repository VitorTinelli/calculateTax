package vitor.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NfeTax {
  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  private UUID id;

  @ManyToOne
  private Nfe nfe;

  @ManyToOne
  private Taxes taxes;

  @NotNull
  private Double valueWithTax;

  @NotNull
  private Double difference;

  @NotBlank
  private String month;

  @NotNull
  private Integer year;

}
