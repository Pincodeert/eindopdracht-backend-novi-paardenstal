package nl.pin.paardenstal.dtos;

import javax.validation.constraints.Min;

public class IdInputDto {

    @Min(value = 1, message = "id moet minimaal 1 zijn")
    public long id;

}
