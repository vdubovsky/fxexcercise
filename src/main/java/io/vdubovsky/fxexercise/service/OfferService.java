package io.vdubovsky.fxexercise.service;

import io.vdubovsky.fxexercise.dto.OfferDto;

public interface OfferService {

    OfferDto getOffer(String instrument);
}
