package nl.pin.paardenstal.dtos;

import javax.validation.constraints.Min;

public class EnrollmentInputDto {

   public Long subscriptionId;

   public Long customerId;

   public Long horseId;

   public String date;

   public boolean isOngoing;

}
