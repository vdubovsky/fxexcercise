package io.vdubovsky.fxexercise.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InstrumentDto {

    private String name;
}
