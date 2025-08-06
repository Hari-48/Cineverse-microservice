package com.hari.tamil_movies.export.repo;



import com.hari.tamil_movies.export.entity.DownloadJob;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository

public interface DownloadJobRepository extends CrudRepository<DownloadJob, Long> {

    @Modifying
    @Transactional
    @Query(value = "update DownloadJob t set t.isReady = :isReady, t.filePath = :filePath where t.id = :id")
    int updateDownloadJobStatus(Character isReady, String filePath, Long id);

    @Modifying
    @Transactional
    @Query(value = "update DownloadJob t set t.itemsTotal = :itemsTotal, t.itemsProcessed = :itemsProcessed, t.progress = :progress, t.progressStage = :progressStage where t.id = :id")
    int updateDownloadJobProgress(Integer itemsTotal, Integer itemsProcessed, Integer progress, String progressStage, Long id);

}

