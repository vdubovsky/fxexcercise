package io.vdubovsky.fxexercise.service.impl;

import io.vdubovsky.fxexercise.entity.CommissionInfo;
import io.vdubovsky.fxexercise.entity.MarketTick;
import io.vdubovsky.fxexercise.repository.CommissionInfoRepository;
import io.vdubovsky.fxexercise.service.CommissionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CommissionServiceImpl implements CommissionService {

    private final CommissionInfoRepository commissionInfoRepository;
    private final CommissionService self;

    public CommissionServiceImpl(CommissionInfoRepository commissionInfoRepository, @Lazy CommissionService self) {
        this.commissionInfoRepository = commissionInfoRepository;
        this.self = self;
    }

    @Override
    public BigDecimal getBid(MarketTick marketTick) {
        BigDecimal commission = self.getCommission().getAmount().multiply(marketTick.getBid());
        return marketTick.getBid().subtract(commission);
    }

    @Override
    public BigDecimal getAsk(MarketTick marketTick) {
        BigDecimal commission = self.getCommission().getAmount().multiply(marketTick.getAsk());
        return marketTick.getAsk().add(commission);
    }

    // TODO add caching
    @Override
    public CommissionInfo getCommission() {
        return commissionInfoRepository.findFirstByOrderByTimestampDesc()
                .orElseThrow(() -> new RuntimeException("Commission not found."));
    }
}
