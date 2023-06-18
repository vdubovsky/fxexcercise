package io.vdubovsky.fxexercise.service;

import io.vdubovsky.fxexercise.entity.Instrument;

public interface InstrumentService {

    boolean instrumentExist(String instrument);

    boolean instrumentAvailable(String instrument);

    Instrument getByName(String instrument);
}
