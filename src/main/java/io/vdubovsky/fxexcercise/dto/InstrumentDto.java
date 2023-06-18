package io.vdubovsky.fxexcercise.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InstrumentDto {

    private String name;
}
