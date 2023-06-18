package io.vdubovsky.fxexercise.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "commission_info")
public class CommissionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 16, scale = 4)
    private BigDecimal amount;

    private LocalDateTime timestamp;
}
