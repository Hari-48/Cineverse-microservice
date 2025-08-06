package com.hari.tamil_movies.export.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name="DOWNLOAD_JOBS")

public @Data
class DownloadJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "PROGRESS_STAGE")
    private String progressStage;

    @Column(name = "PROGRESS")
    private Integer progress;

    @Column(name = "ITEMS_PROCESSED")
    private Integer itemsProcessed;

    @Column(name = "ITEMS_TOTAL")
    private Integer itemsTotal;

    @Column(name = "IS_READY")
    private Character isReady = 'N';

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "REQUEST_TIME")
    private LocalDateTime requestTime;

    @Column(name = "READY_TIME")
    private LocalDateTime readyTime;

    public boolean getIsReady() {
        return isReady != null && isReady.equals('Y');
    }
    public void setIsReady(boolean isReady) {
        this.isReady = isReady ? 'Y' : 'N';
    }

}
