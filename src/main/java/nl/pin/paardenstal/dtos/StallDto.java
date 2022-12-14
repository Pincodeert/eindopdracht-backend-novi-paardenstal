package nl.pin.paardenstal.dtos;

import nl.pin.paardenstal.models.Horse;

public class StallDto {

    private long id;

    private String name;

    private String size;

    private String type;

    private HorseDto horseDto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public HorseDto getHorseDto() {
        return horseDto;
    }

    public void setHorseDto(HorseDto horseDto) {
        this.horseDto = horseDto;
    }
}
