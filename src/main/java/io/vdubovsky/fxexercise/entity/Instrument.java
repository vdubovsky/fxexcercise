package io.vdubovsky.fxexercise.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "dir_instrument")
public class Instrument {

    @Id
    private String name;

    private Boolean isAvailable;
}
