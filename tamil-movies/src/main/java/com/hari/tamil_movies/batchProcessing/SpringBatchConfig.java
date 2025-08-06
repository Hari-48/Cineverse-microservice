package com.hari.tamil_movies.batchProcessing;

import com.hari.tamil_movies.batchProcessing.Model.CustomLineMapper;
import com.hari.tamil_movies.batchProcessing.Model.CustomProcessor;
import com.hari.tamil_movies.batchProcessing.Model.DataRecord;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
// @EnableSpring Batch Processing - annotation is not needed  for latest version (5.0)

@Slf4j
@EnableBatchProcessing
public class SpringBatchConfig {

    public final DataSource dataSource;

    public SpringBatchConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    // step -1 - create the Job and create the step for that
    @Bean
    public Job createJob(JobRepository jobRepository, Step newSteps) {
        return new JobBuilder(LocalDateTime.now().toString(), jobRepository)
                .start(newSteps)
                .build();
    }


    //Step 2 - create a STEP for job with reader , processor and writter
    @Bean
    @Primary
    public Step newSteps(JobRepository jobRepository,
                         PlatformTransactionManager transactionManager
    ) throws IOException, ParseException {
        return new StepBuilder("tamil-movies" + LocalDateTime.now(), jobRepository)
                .<DataRecord, DataRecord>chunk(10, transactionManager)
                .reader(reader(null, null))
                .writer(writers(null))
//                .taskExecutor(taskExecutor()) // 👈 Use TaskExecutor
                .chunk(5)
                .build();
    }


    // create the Reader - to read the data from the give file
    @Bean
    @StepScope
    public FlatFileItemReader<DataRecord> reader(
            @Value("#{jobParameters['filePath']}") String filePath,
            @Value("#{jobParameters['jsonPath']}") String jsonPath
    ) throws IOException, ParseException {
        return new FlatFileItemReaderBuilder<DataRecord>()
                .name("tamil-movies")
//                .resource(new FileSystemResource(filePath))
                .resource(new ClassPathResource(filePath))
                .recordSeparatorPolicy(new DefaultRecordSeparatorPolicy())
                .lineMapper(new CustomLineMapper(jsonColumnNames(jsonPath), ","))
                .linesToSkip(1)
                .build();
    }


    public static ArrayList<String> jsonColumnNames(@Value("#{jobParameters['jsonPath']}") String jsonPath) throws IOException, org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
//        InputStream resource = new FileSystemResource(jsonPath).getInputStream();
        InputStream resource = new ClassPathResource(jsonPath).getInputStream();

        Reader reader = new InputStreamReader(resource);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray jsonArray = (JSONArray) jsonObject.get("fields");
        ArrayList<String> arr = new ArrayList<>();
        for (Object o : jsonArray)
            arr.add((String) o);
        log.info("Columns Name from JSON file ->  {}", jsonArray);
        return arr;
    }

    @Bean
    @StepScope
    public CustomProcessor processors(@Value("#{jobParameters['fileName']}") String filename) {
        return new CustomProcessor(filename);
    }

    @Bean
    @StepScope
    @Transactional
    public JdbcBatchItemWriter<DataRecord> writers(@Value("#{jobParameters['jsonPath']}") String jsonPath)
            throws IOException, ParseException {
        ArrayList<String> fields = new ArrayList<>(jsonColumnNames(jsonPath));
        String table = getTable(jsonPath);

        String queryColumns = String.join(", ", fields);
//        log.info("QueryColumn:{}",queryColumns);
        String valueColumns = fields.stream().map(field -> ":" + field).collect(Collectors.joining(", "));
//        log.info("valueColumns:{}",valueColumns);


        // Construct SQL without ON DUPLICATE KEY UPDATE
        String sql = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                table, queryColumns, valueColumns
        );

        log.info("SQL Statement: {}", sql);

        return new JdbcBatchItemWriterBuilder<DataRecord>()
                .itemSqlParameterSourceProvider(item -> {
                    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
                    mapSqlParameterSource.addValues(item.getDataMap());
                    return mapSqlParameterSource;
                })
                .sql(sql)
                .dataSource(dataSource)
                .build();
    }


    public static String getTable(@Value("#{jobParameters['jsonPath']}") String jsonPath) throws IOException, org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
//        InputStream resource = new FileSystemResource(jsonPath).getInputStream();
        InputStream resource = new ClassPathResource(jsonPath).getInputStream();
        Reader reader = new InputStreamReader(resource);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        return (String) jsonObject.get("table");
    }




//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);
//        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(25);
//        executor.setThreadNamePrefix("batch-thread-");
//        executor.initialize();
//        return executor;
//    }


}
