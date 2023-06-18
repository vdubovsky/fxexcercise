package io.vdubovsky.fxexcercise.service;

import io.vdubovsky.fxexcercise.dto.OfferDto;

public interface OfferService {

    OfferDto getOffer(String instrument);
}
