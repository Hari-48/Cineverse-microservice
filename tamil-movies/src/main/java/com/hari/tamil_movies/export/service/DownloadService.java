package com.hari.tamil_movies.export.service;


import com.hari.tamil_movies.export.entity.DownloadJob;
import com.hari.tamil_movies.export.repo.DownloadJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.File;
import java.time.LocalDateTime;

@Slf4j
@Service
public class DownloadService {


    @Autowired
    private DownloadJobRepository jobRepository;



    public DownloadJob createNewJob(String type, String username) {
        DownloadJob job = new DownloadJob();
        job.setType(type);
        job.setUsername(username);
        job.setRequestTime(LocalDateTime.now());
        job.setProgress(0);
        job.setItemsTotal(0);
        job.setItemsProcessed(0);
        job.setProgressStage("INITIALIZED");
        return jobRepository.save(job);
    }



    //track status
    public DownloadJob getJob(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public File getFile(Long id) {
        DownloadJob job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }
        if (job.getFilePath() == null) {
            return null;
        }
        return new File(job.getFilePath());
    }



    @Async
    public void deleteFile(File file) throws InterruptedException {
        Thread.sleep(60000);
        file.delete();
        log.info("Last exported file deleted successfully..");
    }



    @Transactional
    //update status & file
    public void updateJob(Long id, String filePath, boolean isReady) {

        log.info("Inside updateJob(), transaction active: " + TransactionSynchronizationManager.isActualTransactionActive());

        jobRepository.updateDownloadJobStatus(isReady ? 'Y' : 'N', filePath, id);
    }

}
