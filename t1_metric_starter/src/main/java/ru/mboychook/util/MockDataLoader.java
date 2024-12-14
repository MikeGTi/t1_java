package ru.mboychook.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mboychook.aop.LogDataSourceError;
import ru.mboychook.aop.Track;
import ru.mboychook.model.entity.DataSourceErrorLog;
import ru.mboychook.repository.DataSourceErrorLogRepository;

import java.util.stream.IntStream;

@Setter
@Getter
//@Component
@RequiredArgsConstructor
@Slf4j
public class MockDataLoader {

    private final DataSourceErrorLogRepository dataSourceErrorLogRepository;

    @Value("${t1.mock-data.add-objects-counter}")
    private Integer counter;

    @Track
    @LogDataSourceError
    public void loadData() {
        IntStream.range(0, counter)
                 .forEach(i -> dataSourceErrorLogRepository.save(generateDataSourceErrorLog()));
    }

    public DataSourceErrorLog generateDataSourceErrorLog() {
        DataSourceErrorLog errorLog = new DataSourceErrorLog();
                           errorLog.setMethodSignature("ru.t1.example.anyService.methodWithError");
                           errorLog.setMessage("Any type error");
                           errorLog.setStackTrace("Stack trace of the error");
                    return errorLog;
    }
}