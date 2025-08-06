package com.hari.tamil_movies.export.service;


import com.hari.tamil_movies.export.repo.DownloadJobRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class JobProgressService {

    @Autowired
    private DownloadJobRepository downloadJobRepository;



    @Transactional
    public void updateJobProgress(String stage, Integer itemsTotal, Integer itemsProcessed, Long id) {
        log.info("Transaction active: {}", TransactionSynchronizationManager.isActualTransactionActive());

        Integer progress = 0;
        if (itemsTotal != null && itemsProcessed != null && itemsTotal > 0) {
            BigDecimal progressBigInt = new BigDecimal(itemsProcessed)
                    .divide(new BigDecimal(itemsTotal), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            progress = progressBigInt.intValue();
        }
        downloadJobRepository.updateDownloadJobProgress(itemsTotal, itemsProcessed, progress, stage, id);
    }
}
