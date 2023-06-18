package io.vdubovsky.fxexercise.repository;

import io.vdubovsky.fxexercise.entity.MarketTick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketTickRepository extends JpaRepository<MarketTick, Long> {
    Optional<MarketTick> findFirstByInstrumentNameOrderByTimestampDesc(String instrumentName);
}
