package io.vdubovsky.fxexercise.repository;

import io.vdubovsky.fxexercise.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, String> {
}
