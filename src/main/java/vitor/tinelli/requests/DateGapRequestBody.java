package vitor.tinelli.requests;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DateGapRequestBody {

  private LocalDate startDate;
  private LocalDate endDate;

}
