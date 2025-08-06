package com.hari.tamil_movies.export.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.hari.tamil_movies.export.entity.DownloadJob;
import com.hari.tamil_movies.export.repo.DownloadJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service


public class ExportService
{


    @Autowired
    private DownloadJobRepository jobRepository;

//    @Autowired
//    private MatchesService matchesService;

    @Autowired
    private Environment env;

    @Autowired
    private DownloadService downloadService;



    @Autowired
    private JobProgressService jobProgressService;

    public void updateJobProgressAsync(String stage, Integer itemsTotal, Integer itemsProcessed, Long id) {
        jobProgressService.updateJobProgress(stage, itemsTotal, itemsProcessed, id);
    }


    public File fileLocation(String folder, String fileName ,String exportType) {
        String tempory = env.getProperty("app.paths.root");
        String userHomePath = tempory.replace("[user.home]", System.getProperty("user.home"))
                .replace("\\", File.separator)
                .replace("/", File.separator);

        // Construct target directory: e.g., /home/user/downloads/IPL_MATCH
        File directory = new File(userHomePath + File.separator + "downloads" + File.separator + folder);
        directory.mkdirs(); // Create directory and parents if they don't exist

        // Return file reference: e.g., /home/user/downloads/IPL_MATCH/scores.xlsx

        if (exportType.equalsIgnoreCase("xlsx")){
             fileName =fileName+File.separator+"xlsx";
            return new File(directory, fileName);
    }
        else {
            return new File(directory, fileName+"csv");
        }
    }


//
//    public CompletableFuture<Void> exportMatchData(DownloadJob job) throws JsonProcessingException {
//
//        int totalPage = 1;
//        int pageSize = 50000;
//        int page = 0;
//
//        int start = (int) System.currentTimeMillis();
//        File file = fileLocation("EXPORT", "IPL","xlsx");
//
//        List<Cricket> matches = new ArrayList<>();
//
//        log.info("xlsx" + " Export: Initializing job status.");
//        updateJobProgressAsync("FETCH_ITEMS", 0, 0, job.getId());
//        log.info("xlsx" + " Export: Starting fetch.");
//        Integer count = null;
////        count = matchesService.getAllMatches().size();
//
//        count=cricRepo.getCount();
//
//        for (int i = 0; i < totalPage; i++) {
//            Page<Cricket> matchesPerPage = null;
//
////            matchesPerPage = matchesService.getAllMatches1(0, 500);
//            matchesPerPage = cricRepo.getAllData(PageRequest.of(page,pageSize,Sort.Direction.ASC,"name"));
//
//            log.debug("xlsx" + " Export: Found {} items to export in page {} of {} pages and total {} items.",
//                    matchesPerPage.getContent().size(),
//                    (i + 1),
//                    matchesPerPage.getTotalPages(),
//                    matchesPerPage.getTotalElements());
//            matches.addAll(matchesPerPage.getContent());
//            updateJobProgressAsync("FETCH_ITEMS", count, matches.size(), job.getId());
//            totalPage = matchesPerPage.getTotalPages();
//            page++;
//        }
//
//
//
//        log.info("xlsx" + " Export: Fetch complete.");
//        updateJobProgressAsync("FILE_WRITE", count, matches.size(), job.getId());
//
//
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//
//        //CONVERT TO MAP
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
//        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
//        mapper.setDateFormat(format);
//        String jsonString = mapper.writeValueAsString(matches);
//        List<Map<String, Object>> tradesMaps = mapper.readValue(jsonString, new TypeReference<>() {
//        });
//
//        //Excel and CSV Logic
//        try {
//            if ("xlsx".equalsIgnoreCase("xlsx")) {
//                log.info("00000");
//
//                log.info("File :{}",file);
//
//                file = toXlSX(tradesMaps, file, job);
//                log.info("FILE_WRITE " + file);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info(e.getMessage());
//        }
//        log.info("File Path ..." + file.getAbsolutePath());
//        downloadService.updateJob(job.getId(), file.getAbsolutePath(), true);
//        log.info("xlsx" + " Export: Write complete.");
//        log.info("Execution time taken for exporting live trades {}", ((int) System.currentTimeMillis()) - start);
//        return CompletableFuture.completedFuture(null);
//    }





    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursively(f);
                }
            }
        }
        file.delete();
    }






    public File toXlSX(List<Map<String, Object>> list, File directory, DownloadJob job) throws IOException {

        if (!directory.exists()) {
            directory.mkdirs();
        }
        else {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        deleteRecursively(file);
                    }
                }
            }
        }

        // Split data into chunks of 100
        int chunkSize = 100000;
        int fileCount = 0;
        int totalProcessed = 0;
        List<File> excelFiles = new ArrayList<>();

        for (int start = 0; start < list.size(); start += chunkSize) {
            int end = Math.min(start + chunkSize, list.size());
            List<Map<String, Object>> subList = list.subList(start, end);

            fileCount++;
            File excelFile = new File(directory, "tamil-mov" + "_" + fileCount + ".xlsx");
            writeExcelFile(subList, excelFile);  // write a single file




            excelFiles.add(excelFile);
            totalProcessed += subList.size();
            if (job != null) {
                updateJobProgressAsync("FILE_WRITE", list.size(), totalProcessed, job.getId());
            }
        }

        // Create ZIP
        File zipFile = new File(directory, "tamil-movies-2011-2019"+LocalDateTime.now() + ".zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (File file : excelFiles) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }

        log.info("ZIP file created with {} Excel files: {}", fileCount, zipFile.getAbsolutePath());
        return zipFile;
    }


    private void writeExcelFile(List<Map<String, Object>> data, File file) throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();

        List<String> headers = data.stream()
                .flatMap(map -> map.keySet().stream())
                .distinct()
                .collect(Collectors.toList());

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle left = workbook.createCellStyle();
        left.setAlignment(HorizontalAlignment.LEFT);

        CellStyle numeric = workbook.createCellStyle();
        numeric.setAlignment(HorizontalAlignment.RIGHT);
        numeric.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));

        SXSSFSheet sheet = workbook.createSheet("Sheet1");
        int rowIndex = 0;

        // Header
        Row headerRow = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        for (Map<String, Object> rowData : data) {
            Row row = sheet.createRow(rowIndex++);
            for (int i = 0; i < headers.size(); i++) {
                Object value = rowData.get(headers.get(i));
                Cell cell = row.createCell(i);

                if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                    cell.setCellStyle(numeric);
                } else if (value != null) {
                    cell.setCellValue(value.toString());
                    cell.setCellStyle(left);
                }
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        } finally {
            workbook.close();
        }
    }


    public <T> CompletableFuture<Void> export(
            DownloadJob job,
            Long count,
            Function<Pageable, Page<T>> fetcher,
            String sortField,
            String fileType,String fileName
    ) throws JsonProcessingException {

        int pageSize = 50000;
        int page = 0;
        int totalPages = 1;

        long start = System.currentTimeMillis();
        File file = fileLocation("EXPORT", fileName, fileType);

        List<T> matches = new ArrayList<>();

        log.info("{} Export: Initializing job status.", fileType);
        updateJobProgressAsync("FETCH_ITEMS", 0, 0, job.getId());

        do {
            Page<T> pageResult = fetcher.apply(PageRequest.of(page, pageSize, Sort.Direction.ASC, sortField));
            matches.addAll(pageResult.getContent());
            updateJobProgressAsync("FETCH_ITEMS", Math.toIntExact(count), matches.size(), job.getId());

            totalPages = pageResult.getTotalPages();
            page++;
        } while (page < totalPages);

        log.info("{} Export: Fetch complete. Total items: {}", fileType, matches.size());
        updateJobProgressAsync("FILE_WRITE", Math.toIntExact(count), matches.size(), job.getId());

        // Convert to List<Map<String, Object>>
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

        String jsonString = mapper.writeValueAsString(matches);
        List<Map<String, Object>> dataMaps = mapper.readValue(jsonString, new TypeReference<>() {});

        // Excel/CSV logic
        try {
            if ("xlsx".equalsIgnoreCase(fileType)) {
                file = toXlSX(dataMaps, file, job);
            }
            // else if for CSV or JSON (future extension)
        } catch (Exception e) {
            log.error("Error writing file: {}", e.getMessage());
            e.printStackTrace();
        }

        downloadService.updateJob(job.getId(), file.getAbsolutePath(), true);
        log.info("{} Export: File written at {}", fileType, file.getAbsolutePath());
        log.info("Execution time taken: {} ms", (System.currentTimeMillis() - start));

        return CompletableFuture.completedFuture(null);
    }


}
