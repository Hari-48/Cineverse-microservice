package com.hari.tamil_movies.export.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hari.tamil_movies.export.entity.DownloadJob;
import com.hari.tamil_movies.export.service.DownloadService;
import com.hari.tamil_movies.export.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Slf4j
@RestController
public class exportController {

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private ExportService export;

//
//    @Autowired
//    private CricRepo cricRepo;

//
//    @PostMapping("/export")
//    public ResponseEntity<?> exportMatchData(){
//        try {
//            DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_hhmmss");
//            String currentDateTime = dateFormatter.format(new Date());
//            String fileName = "export_matches" + "_" + currentDateTime;
//            DownloadJob job = downloadService.createNewJob(fileName, "hari");
//
//            CompletableFuture<Void> future = export.export(job);
//            return new ResponseEntity<>(job, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Current Deals Export Operation Failed : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("/downloads/jobs/{id}")
    public ResponseEntity<?> getJob1(
            @PathVariable Long id
    ) {
        DownloadJob job = downloadService.getJob(id);
        if (job == null) {
            return new ResponseEntity<>("No such job exists.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(job, HttpStatus.OK);
    }



    @GetMapping("/downloads/jobs/{id}/get-file")
    public ResponseEntity<?> getFileFromJobID(
            @PathVariable Long id
//            HttpServletRequest request
    ) throws IOException {
        try {

            File fileToProvide = downloadService.getFile(id);

            log.info("FILEPROVIDE--------------------------" + fileToProvide);



            if (fileToProvide == null) {
                return new ResponseEntity<>("File may not be ready yet.", HttpStatus.NOT_FOUND);
            }
            if (!fileToProvide.isFile()) {
                return new ResponseEntity<>("No such file exists.", HttpStatus.NOT_FOUND);
            }
            if (fileToProvide.isDirectory()) {
                return new ResponseEntity<>("Requested file is a directory.", HttpStatus.NOT_FOUND);
            }
            Path filePath = Paths.get(fileToProvide.getCanonicalPath());
            log.info("PATH_______________________" + filePath);
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = null;
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("No such job exists." + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



//    public CompletableFuture<Void> exportData(DownloadJob job) throws JsonProcessingException {
//        int pageSize = 50000;
//        return export.export(
//                job,
//                matchesRepo.getCount(),
//                pageable -> matchesRepo.getAllData(pageable), // function for page fetching
//                "matchId", // sort field
//                "xlsx",  // file type,
//                "MATCH"
//        );
//    }

}
