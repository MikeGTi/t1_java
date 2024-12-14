package ru.mboychook.util;

import org.springframework.stereotype.Component;

@Component
public class MetricStatisticMapper {

    public static MetricStatistic toEntity(ru.mboychook.model.dto.MetricStatisticDto dto) {
        return MetricStatistic.builder()
                .executionTime(dto.getExecutionTime())
                .exceededOnTime(dto.getExceededOnTime())
                .methodName(dto.getMethodName())
                .methodArgs(dto.getMethodArgs())
                .build();
    }

    public static ru.mboychook.model.dto.MetricStatisticDto toDto(MetricStatistic entity) {
        return ru.mboychook.model.dto.MetricStatisticDto.builder()
                .id(entity.getId())
                .executionTime(entity.getExecutionTime())
                .exceededOnTime(entity.getExceededOnTime())
                .methodName(entity.getMethodName())
                .methodArgs(entity.getMethodArgs())
                .build();
    }

}
