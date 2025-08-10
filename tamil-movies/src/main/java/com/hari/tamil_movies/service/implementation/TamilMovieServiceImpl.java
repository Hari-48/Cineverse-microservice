package com.hari.tamil_movies.service.implementation;

import com.hari.tamil_movies.entity.Movies;
import com.hari.tamil_movies.export.entity.DownloadJob;
import com.hari.tamil_movies.export.service.ExportService;
import com.hari.tamil_movies.repo.TamilMovieRepo;
import com.hari.tamil_movies.service.TamilMoviesService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class TamilMovieServiceImpl implements TamilMoviesService {

    @Autowired
    private ExportService service;

    @Autowired
    private TamilMovieRepo tamilMovieRepo;

    @SneakyThrows
    @Override
    public CompletableFuture<Void> exportData(DownloadJob downloadJob) {
        int pageSize = 50000;
        return service.export(
                downloadJob,
                tamilMovieRepo.getCount(),
                pageable -> tamilMovieRepo.getAllData(pageable), // function for page fetching
                "rating", // sort field
                "xlsx",  // file type,
                "TAMIL-MOVIES-2011-2019"
        );
    }


    @Cacheable(value = "movies")
    @Override
    public List<String> findAll(PageRequest pageRequest) {
        System.out.println("Fetching from DB for movie id ");

        List<String> movies = new ArrayList<>();
        Page<Movies> pageRequestList =tamilMovieRepo.findAll(pageRequest);
        pageRequestList.stream().forEach(f->movies.add(f.getMovieName()));
        return movies;
    }





}
