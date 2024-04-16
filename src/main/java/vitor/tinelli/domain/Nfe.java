package vitor.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
public class Nfe {

  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  private UUID id;

  @NotNull
  private Long number;

  @NotNull
  private LocalDate date;

  @NotNull
  private Double value;

}
