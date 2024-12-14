package ru.mboychook.mapper;

import ru.mboychook.model.dto.MetricDto;
import ru.mboychook.model.entity.Metric;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MetricMapper {
    MetricDto toDto(Metric metric);

    Metric toEntity(MetricDto metricDto);
}



