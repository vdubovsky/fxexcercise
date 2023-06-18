package io.vdubovsky.fxexercise.service.impl;

import io.vdubovsky.fxexercise.dto.InstrumentDto;
import io.vdubovsky.fxexercise.dto.OfferDto;
import io.vdubovsky.fxexercise.entity.MarketTick;
import io.vdubovsky.fxexercise.exception.OfferNotAvailableException;
import io.vdubovsky.fxexercise.service.CommissionService;
import io.vdubovsky.fxexercise.service.InstrumentService;
import io.vdubovsky.fxexercise.service.MarketTickService;
import io.vdubovsky.fxexercise.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
