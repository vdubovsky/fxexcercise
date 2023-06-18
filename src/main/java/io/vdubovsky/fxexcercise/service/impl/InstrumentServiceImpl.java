package io.vdubovsky.fxexcercise.service.impl;

import io.vdubovsky.fxexcercise.entity.Instrument;
import io.vdubovsky.fxexcercise.exception.InstrumentNotAvailableException;
import io.vdubovsky.fxexcercise.repository.InstrumentRepository;
import io.vdubovsky.fxexcercise.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentRepository instrumentRepository;

    // TODO add caching
    @Override
    public boolean instrumentExist(String instrument) {
        Boolean isExist = instrumentRepository.existsById(instrument);
        return isExist == null ? false : isExist;
    }

    // TODO add caching
    @Override
    public boolean instrumentAvailable(String instrument) {
        Boolean isAvailable = instrumentRepository.findById(instrument)
                .map(Instrument::getIsAvailable)
                .orElseThrow(() -> new InstrumentNotAvailableException("Instrument not found, instrument=" + instrument));

        return isAvailable == null ? false : isAvailable;
    }

    // TODO add caching
    @Override
    public Instrument getByName(String instrument) {
        return instrumentRepository.findById(instrument)
                .orElseThrow(() -> new InstrumentNotAvailableException("Instrument not found, instrument=" + instrument));
    }
}
