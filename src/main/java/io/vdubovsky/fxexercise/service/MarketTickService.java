package io.vdubovsky.fxexercise.service;

import io.vdubovsky.fxexercise.entity.MarketTick;
import io.vdubovsky.fxexercise.message.api.MarketTickMessage;

import java.util.List;
import java.util.Optional;

public interface MarketTickService {

    void receiveTicks(List<MarketTickMessage> marketTicks);

    Optional<MarketTick> getLastTick(String instrument);
}
