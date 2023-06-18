package io.vdubovsky.fxexcercise.service.impl;

import io.vdubovsky.fxexcercise.dto.InstrumentDto;
import io.vdubovsky.fxexcercise.dto.OfferDto;
import io.vdubovsky.fxexcercise.entity.MarketTick;
import io.vdubovsky.fxexcercise.exception.OfferNotAvailableException;
import io.vdubovsky.fxexcercise.service.CommissionService;
import io.vdubovsky.fxexcercise.service.InstrumentService;
import io.vdubovsky.fxexcercise.service.MarketTickService;
import io.vdubovsky.fxexcercise.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final MarketTickService marketTickService;
    private final InstrumentService instrumentService;
    private final CommissionService commissionService;

    @Override
    public OfferDto getOffer(String instrument) {
        if (!instrumentService.instrumentAvailable(instrument)) {
            throw offerNotAvailableException(instrument);
        }
        return marketTickService.getLastTick(instrument)
                .map(this::mapToOfferDto)
                .orElseThrow(() -> offerNotAvailableException(instrument));
    }

    private OfferDto mapToOfferDto(MarketTick marketTick) {
        return new OfferDto()
                .setBid(commissionService.getBid(marketTick))
                .setAsk(commissionService.getAsk(marketTick))
                .setInstrument(new InstrumentDto().setName(marketTick.getInstrument().getName()));
    }

    private static OfferNotAvailableException offerNotAvailableException(String instrument) {
        return new OfferNotAvailableException(String.format("Offer with instrument: %s is not available", instrument));
    }
}
