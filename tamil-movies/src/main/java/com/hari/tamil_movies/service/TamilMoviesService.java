package com.hari.tamil_movies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hari.tamil_movies.export.entity.DownloadJob;
import com.hari.tamil_movies.export.service.ExportService;
import com.hari.tamil_movies.repo.TamilMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface TamilMoviesService {

//    @Autowired
//    private ExportService service;
//
//    @Autowired
//    private TamilMovieRepo tamilMovieRepo;


    public CompletableFuture<Void> exportData(DownloadJob downloadJob);

    public List<String> findAll(PageRequest pageRequest);


//    public CompletableFuture<Void> exportData(DownloadJob job) throws JsonProcessingException {
//        int pageSize = 50000;
//        return service.export(
//                job,
//                tamilMovieRepo.getCount(),
//                pageable -> tamilMovieRepo.getAllData(pageable), // function for page fetching
//                "rating", // sort field
//                "xlsx",  // file type,
//                "TAMIL-MOVIES-2011-2019"
//        );
//    }
}
