package ru.mboychook.mapper;

import org.mapstruct.Mapper;
import ru.mboychook.model.entity.DataSourceErrorLog;
import ru.mboychook.model.dto.DataSourceErrorLogDto;


@Mapper(componentModel = "spring")
public interface DataSourceErrorLogMapper {

    DataSourceErrorLogDto toDto(DataSourceErrorLog dataSourceErrorLog);

    DataSourceErrorLog toEntity(DataSourceErrorLogDto dataSourceErrorLogDto);
}
