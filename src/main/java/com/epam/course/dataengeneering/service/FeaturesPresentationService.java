package com.epam.course.dataengeneering.service;

import com.epam.course.dataengeneering.data.mapper.ToColumnListMapper;
import com.epam.course.dataengeneering.data.mapper.ToEntityMapper;
import com.epam.course.dataengeneering.data.provider.impl.CsvDataProvider;
import com.epam.course.dataengeneering.data.repository.StuffRepository;
import com.epam.course.dataengeneering.model.Stuff;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
@Transactional
@Slf4j
public class FeaturesPresentationService {

    @Value("${csv.batch.limit}")
    private Integer batchLimit;
    @Value("${csv.datasource.name}")
    private String dataSourceName;
    @Autowired
    private StuffRepository repository;
    @Autowired
    private CsvDataProvider dataProvider;
    @Autowired
    private ToColumnListMapper toColumnListMapper;
    @Autowired
    private ToEntityMapper toEntityMapper;

    @SneakyThrows
    public void saveFromDataProvider() {

        final Path path = Paths.get(ClassLoader.getSystemResource(dataSourceName).toURI());

        dataProvider.batchRead(path, batchLimit)
                .forEach(entities -> {
                    final Set<Stuff> batch = entities
                            .map(toColumnListMapper::map).map(toEntityMapper::map).collect(Collectors.toSet());
                    repository.saveAll(batch);
                    log.info("Saving in progress... " + batch.size() + " records persisted in batch.");
                });
    }

    public List<Object[]> findIssuesAmountOfStateByCompany(String company) {
        return repository.findIssuesAmountOfStateByCompany(company);
    }

    public List<Object[]> findIssuesAmountOfStateByCompany(Date startDate, Date endDate) {
        return repository.findProductsAndIssuesByDateRange(startDate, endDate);
    }


}
