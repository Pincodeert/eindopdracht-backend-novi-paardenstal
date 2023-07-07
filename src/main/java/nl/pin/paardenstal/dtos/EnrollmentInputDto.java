package nl.pin.paardenstal.dtos;

import javax.validation.constraints.Min;

public class EnrollmentInputDto {

   @Min(value = 1, message = "id moet minimaal 1 zijn")
   public Long id1;

   @Min(value = 1, message = "id moet minimaal 1 zijn")
   public Long id2;

   public String date;

   public boolean isOngoing;

}
