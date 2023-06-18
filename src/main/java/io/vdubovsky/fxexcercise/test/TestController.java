package io.vdubovsky.fxexcercise.test;

import io.vdubovsky.fxexcercise.entity.CommissionInfo;
import io.vdubovsky.fxexcercise.entity.Instrument;
import io.vdubovsky.fxexcercise.message.MarketTickMessageListener;
import io.vdubovsky.fxexcercise.repository.CommissionInfoRepository;
import io.vdubovsky.fxexcercise.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final CommissionInfoRepository commissionInfoRepository;
    private final InstrumentRepository instrumentRepository;

    private final MarketTickMessageListener marketTickMessageListener;

    @PostMapping("/init")
    public void initTestData() {
        log.info("Initializing initial data...");
        commissionInfoRepository.save(new CommissionInfo().setAmount(BigDecimal.valueOf(0.01d))).setTimestamp(LocalDateTime.now());

        instrumentRepository.save(new Instrument().setName("TSLA").setIsAvailable(true));
        instrumentRepository.save(new Instrument().setName("EUR/USD").setIsAvailable(true));
        instrumentRepository.save(new Instrument().setName("BTC/USD").setIsAvailable(false));
    }

    @PostMapping("/tick")
    public void newTick(@RequestBody String message) {
        marketTickMessageListener.onMessage(message);
    }
}
