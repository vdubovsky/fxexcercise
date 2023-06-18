package io.vdubovsky.fxexercise.integration;

import io.vdubovsky.fxexercise.FxExcerciseApplication;
import io.vdubovsky.fxexercise.dto.OfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FxExcerciseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OfferControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    void add() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/test/init"),
                HttpMethod.POST, HttpEntity.EMPTY, String.class);
    }

    @Test
    @RepeatedTest(10)
    void checkLatestTick() throws Exception {
        // Given
        String tick1 = "4242,EUR/USD,1.110,1.220,16-06-2023 12:14:22:199\n" +
                "4243,BTC/USD,26635.0,26645.0,16-06-2023 12:14:22:199";

        String tick2 = "4244,EUR/USD,1.115,1.225,16-06-2023 12:14:22:189";
        String tick3 = "4245,EUR/USD,1.113,1.224,16-06-2023 12:14:22:378";

        String tick4 = "4246,EUR/USD,1.111,1.221,16-06-2023 12:14:22:559\n" +
                "4247,BTC/EUR,26619.7,26639.2,16-06-2023 12:14:22:199";

        String tick5 = "4248,EUR/USD,1.110,1.220,16-06-2023 12:14:22:331";

        List<String> ticks = Arrays.asList(tick1, tick2, tick3, tick4, tick5);

        // When
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        ticks.forEach(t -> executorService.submit(() -> {
            sendTick(t);
            latch.countDown();
        }));

        latch.await();

        // Then
        ResponseEntity<OfferDto> responseEurUsd = restTemplate.exchange(
                createURLWithPort("/api/v1/offers?instrument=EUR/USD"),
                HttpMethod.GET, HttpEntity.EMPTY, OfferDto.class);
        OfferDto offer = responseEurUsd.getBody();

        assertEquals(0, new BigDecimal("1.09989").compareTo(offer.getBid()));
        assertEquals(0, new BigDecimal("1.23321").compareTo(offer.getAsk()));

        // BTC/USD is not available in DB
        ResponseEntity<OfferDto> responseBtcUsd = restTemplate.exchange(
                createURLWithPort("/api/v1/offers?instrument=BTC/USD"),
                HttpMethod.GET, HttpEntity.EMPTY, OfferDto.class);
        assertEquals(500, responseBtcUsd.getStatusCode().value());

        // BTC/EUR is not presented in DB
        ResponseEntity<OfferDto> responseBtcEur = restTemplate.exchange(
                createURLWithPort("/api/v1/offers?instrument=BTC/EUR"),
                HttpMethod.GET, HttpEntity.EMPTY, OfferDto.class);
        assertEquals(500, responseBtcUsd.getStatusCode().value());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private void sendTick(String tick) {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/test/tick"),
                HttpMethod.POST, new HttpEntity<>(tick), String.class);
    }
}
