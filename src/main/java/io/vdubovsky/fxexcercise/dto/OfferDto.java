package io.vdubovsky.fxexcercise.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OfferDto {

    private InstrumentDto instrument;

    private BigDecimal bid;

    private BigDecimal ask;
}
