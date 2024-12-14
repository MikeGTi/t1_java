package ru.mboychook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mboychook.model.entity.DataSourceErrorLog;

public interface DataSourceErrorLogRepository extends JpaRepository<DataSourceErrorLog, Long> {
}