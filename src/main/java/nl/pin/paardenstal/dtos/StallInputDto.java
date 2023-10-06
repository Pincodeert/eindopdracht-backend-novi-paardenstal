package nl.pin.paardenstal.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class StallInputDto {

    @Size(min=2, max=60, message = "stalnaam moet tussen 2 en 60 tekens lang zijn.")
    private String name;

    @NotBlank(message = "verplicht invulveld")
    private String size;

    @NotBlank(message = "verplicht invulveld")
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
