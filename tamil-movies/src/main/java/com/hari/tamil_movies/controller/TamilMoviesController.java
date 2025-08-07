package com.hari.tamil_movies.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hari.tamil_movies.export.entity.DownloadJob;
import com.hari.tamil_movies.export.service.DownloadService;
import com.hari.tamil_movies.repo.TamilMovieRepo;
import com.hari.tamil_movies.service.TamilMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/tamil-movies")
public class TamilMoviesController {

    @Autowired
    private TamilMovieRepo tamilMovieRepo;


    @Autowired
    private DownloadService downloadService;

    @Autowired

    private TamilMoviesService tamilMoviesService;

    @GetMapping("/get-all")
    private ResponseEntity<?> getAllMovies() {
        return new ResponseEntity<>(tamilMovieRepo.findAll(), HttpStatus.OK);
    }










    @PostMapping("/export")
    public ResponseEntity<?> exportMatchData() {
        try {
            DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_hhmmss");
            String currentDateTime = dateFormatter.format(new Date());
            String fileName = "export_matches" + "_" + currentDateTime;
            DownloadJob job = downloadService.createNewJob(fileName, "hari");
            CompletableFuture<Void> future = tamilMoviesService.exportData(job);
//            CompletableFuture<Void> future = export.exportMatchData(job);
            return new ResponseEntity<>(job, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Current Deals Export Operation Failed : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




//
//    @Scheduled(fixedRate = 10000)
//    public void test() throws JsonProcessingException {
//        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_hhmmss");
//        String currentDateTime = dateFormatter.format(new Date());
//        String fileName = "export_matches" + "_" + currentDateTime;
//        DownloadJob job = downloadService.createNewJob(fileName, "hari-scheduler");
//        tamilMoviesService.exportData(job);
//    }








}
