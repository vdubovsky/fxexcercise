package io.vdubovsky.fxexcercise.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "market_tick")
public class MarketTick {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Instrument instrument;

    @Column(precision = 16, scale = 4)
    private BigDecimal bid;

    @Column(precision = 16, scale = 4)
    private BigDecimal ask;

    private LocalDateTime timestamp;
}
