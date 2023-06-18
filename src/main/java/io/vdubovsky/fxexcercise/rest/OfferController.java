package io.vdubovsky.fxexcercise.rest;

import io.vdubovsky.fxexcercise.dto.OfferDto;
import io.vdubovsky.fxexcercise.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping()
    public ResponseEntity<OfferDto> getOffer(@RequestParam(value = "instrument") String instrument){
        return ResponseEntity.ok(offerService.getOffer(instrument));
    }
}
