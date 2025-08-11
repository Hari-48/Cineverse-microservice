package com.hari.tamil_movies.batchProcessing.controller;


import com.hari.tamil_movies.batchProcessing.SpringBatchConfig;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/data-loading")
public class controller {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;


    @Autowired
    private SpringBatchConfig springBatchConfig;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private Step steps;


    @PostMapping("/")
    private String jobLaunchers(
//            @RequestParam String dataSet
    ) throws IOException {

        String filePath = new ClassPathResource("spring-batch/tamilmovies.csv").getPath();
        String jsonPath = new ClassPathResource("spring-batch/tamilmovies.json").getPath();

        String fileName = getFileNameFromFilePath(filePath);

        Job newJob = springBatchConfig.createJob(jobRepository, steps);

        JobParameters jobParam = new JobParametersBuilder()
                .addString("name", "tamil-movies")
                .addString("filePath", filePath)
                .addString("jsonPath", jsonPath)
                .addString("fileName", fileName)
                .toJobParameters();

        try {
            final JobExecution jobExecution = jobLauncher.run(newJob, jobParam);
            return jobExecution.getStatus().toString();
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            return "Job failed with exception : " + e.getMessage();
        }
    }


    private String getFileNameFromFilePath(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    private File saveMultipartFile(MultipartFile multipartFile) throws IOException, IOException {
        File tempFile = File.createTempFile("uploaded-", "-" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        tempFile.deleteOnExit(); // Automatically delete the file when the JVM exits
        return tempFile;
    }


//    @GetMapping("/table")
//    public ResponseEntity<?> createTable(MultipartFile file) throws IOException {
//        File tempFile = saveMultipartFile(file);
//        String tempFilePath = tempFile.getAbsolutePath();
//        String fileName = getFileNameFromFilePath(tempFilePath);
//        String tableName = getTableName(fileName);
//        String columnName = getFields(tempFilePath);
//        return springBatchConfig.createTable(columnName,tableName);
//    }


    public String getTableName(String fileName) {
        String[] parts = fileName.split("-");
        return parts[parts.length - 1].split("\\.")[0].toUpperCase(Locale.ROOT);
    }


    public String getFields(String tempFilePath) throws IOException {

        List<String> fields = new ArrayList<>();
        String columnDefinitions;

        try (BufferedReader br = new BufferedReader(new FileReader(tempFilePath))) {
            // Read the first line of the CSV file
            String firstLine = br.readLine();

            if (firstLine != null) {
                // Split the line by commas to get individual fields
                String[] columns = firstLine.split(",");
                // Add fields to the list
                for (String column : columns) {
                    fields.add(column.trim());

                }
            }
            columnDefinitions = fields.stream()
                    .map(column -> {
                        if (fields.indexOf(column) == 0) {
                            return column + " VARCHAR(50) PRIMARY KEY";
                        } else {
                            return column + " VARCHAR(50)";
                        }
                    })
                    .collect(Collectors.joining(", "));

            log.info(columnDefinitions);
        }
        // Log the extracted columns
        log.info("Columns extracted: {}", fields);
        return columnDefinitions;
    }


}
