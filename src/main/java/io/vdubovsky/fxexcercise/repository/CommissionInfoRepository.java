package io.vdubovsky.fxexcercise.repository;

import io.vdubovsky.fxexcercise.entity.CommissionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommissionInfoRepository extends JpaRepository<CommissionInfo, Long> {

    Optional<CommissionInfo> findFirstByOrderByTimestampDesc();
}
