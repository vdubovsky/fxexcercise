package io.vdubovsky.fxexcercise.service;

import io.vdubovsky.fxexcercise.entity.Instrument;

public interface InstrumentService {

    boolean instrumentExist(String instrument);

    boolean instrumentAvailable(String instrument);

    Instrument getByName(String instrument);
}
