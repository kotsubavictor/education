package server.domain;

import javax.persistence.Column;
import javax.persistence.Id;

public class SonoffRele {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "POWER")
    private Boolean power;
}
