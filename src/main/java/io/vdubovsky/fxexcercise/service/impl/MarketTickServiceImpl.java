package io.vdubovsky.fxexcercise.service.impl;

import io.vdubovsky.fxexcercise.entity.MarketTick;
import io.vdubovsky.fxexcercise.message.api.MarketTickMessage;
import io.vdubovsky.fxexcercise.repository.MarketTickRepository;
import io.vdubovsky.fxexcercise.service.InstrumentService;
import io.vdubovsky.fxexcercise.service.MarketTickService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class MarketTickServiceImpl implements MarketTickService {

    private final MarketTickRepository marketTickRepository;
    private final InstrumentService instrumentService;

    @Override
    public void receiveTicks(List<MarketTickMessage> marketTicks) {
        List<MarketTick> ticks = marketTicks.stream()
                .filter(tick -> instrumentService.instrumentExist(tick.getInstrument()))
                .map(this::mapToMarketTick)
                .collect(Collectors.toList());

        marketTickRepository.saveAll(ticks);
    }

    @Override
    public Optional<MarketTick> getLastTick(String instrument) {
        return marketTickRepository.findFirstByInstrumentNameOrderByTimestampDesc(instrument);
    }

    private MarketTick mapToMarketTick(MarketTickMessage message) {
        return new MarketTick()
                .setId(message.getId())
                .setBid(message.getBid())
                .setAsk(message.getAsk())
                .setInstrument(instrumentService.getByName(message.getInstrument()))
                .setTimestamp(message.getTimestamp());
    }

    ;
}
