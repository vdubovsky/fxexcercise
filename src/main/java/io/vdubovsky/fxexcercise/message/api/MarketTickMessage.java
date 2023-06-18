package io.vdubovsky.fxexcercise.message.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class MarketTickMessage {

    private Long id;

    private String instrument;

    private BigDecimal bid;

    private BigDecimal ask;

    private LocalDateTime timestamp;
}
