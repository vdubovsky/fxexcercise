package io.vdubovsky.fxexcercise.repository;

import io.vdubovsky.fxexcercise.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, String> {
}
