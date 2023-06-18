package io.vdubovsky.fxexercise.service;

import io.vdubovsky.fxexercise.entity.CommissionInfo;
import io.vdubovsky.fxexercise.entity.MarketTick;

import java.math.BigDecimal;

public interface CommissionService {

    BigDecimal getBid(MarketTick marketTick);

    BigDecimal getAsk(MarketTick marketTick);

    CommissionInfo getCommission();
}
