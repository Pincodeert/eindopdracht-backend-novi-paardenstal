package nl.pin.paardenstal.models;

import javax.persistence.*;

@Entity
@Table(name = "cancellations")
public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;




}
