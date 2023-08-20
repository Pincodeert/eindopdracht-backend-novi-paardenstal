package nl.pin.paardenstal.dtos;

public class StallDto {

    private Long id;

    private String name;

    private String size;

    private String type;

    private boolean isOccupied;

    private HorseDto horse;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public HorseDto getHorse() {
        return horse;
    }

    public void setHorse(HorseDto horse) {
        this.horse = horse;
    }



}
