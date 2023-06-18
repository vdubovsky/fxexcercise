package io.vdubovsky.fxexcercise.service;

import io.vdubovsky.fxexcercise.entity.MarketTick;
import io.vdubovsky.fxexcercise.message.api.MarketTickMessage;

import java.util.List;
import java.util.Optional;

public interface MarketTickService {

    void receiveTicks(List<MarketTickMessage> marketTicks);

    Optional<MarketTick> getLastTick(String instrument);
}
