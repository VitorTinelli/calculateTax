package vitor.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@AllArgsConstructor
@RequiredArgsConstructor

public class Taxes {
  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  private UUID id;

  @NotBlank
  private String name;

  @NotNull
  private Double aliquot;
}
