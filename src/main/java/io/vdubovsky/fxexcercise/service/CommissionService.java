package io.vdubovsky.fxexcercise.service;

import io.vdubovsky.fxexcercise.entity.CommissionInfo;
import io.vdubovsky.fxexcercise.entity.MarketTick;

import java.math.BigDecimal;

public interface CommissionService {

    BigDecimal getBid(MarketTick marketTick);

    BigDecimal getAsk(MarketTick marketTick);

    CommissionInfo getCommission();
}
