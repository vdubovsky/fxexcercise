package io.vdubovsky.fxexercise.message;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.vdubovsky.fxexercise.message.api.MarketTickMessage;
import io.vdubovsky.fxexercise.service.MarketTickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MarketTickMessageListener {

    private static final Character CSV_SEPARATOR = ',';
    private static final String LOCAL_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss:SSS";

    private final MarketTickService marketTickService;

    public void onMessage(String message) {
        log.trace("Incoming Market Tick Message: {}", message);
        List<MarketTickMessage> ticks = new ArrayList<>();
        try (CSVReader reader = createReader(message)) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                ticks.add(new MarketTickMessage()
                        .setId(Long.parseLong(line[0]))
                        .setInstrument(line[1])
                        .setBid(new BigDecimal(line[2]))
                        .setAsk(new BigDecimal(line[3]))
                        .setTimestamp(LocalDateTime.parse(line[4], DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT))));
            }
        } catch (Exception e) {
            log.error("Exception occurred while processing tick message, message={}", message);
            throw new RuntimeException(String.format("Exception occurred while processing tick message, message=%s", message));
        }

        marketTickService.receiveTicks(ticks);
    }

    private CSVReader createReader(String message) {
        return new CSVReaderBuilder(new StringReader(message))
                .withCSVParser(new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build())
                .build();
    }
}
