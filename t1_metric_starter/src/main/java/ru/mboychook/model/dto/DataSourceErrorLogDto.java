package ru.mboychook.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.mboychook.model.entity.DataSourceErrorLog;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link DataSourceErrorLog}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceErrorLogDto implements Serializable {
    @JsonProperty("stack_trace")
    private String stackTrace;

    @JsonProperty("message")
    private String message;

    @JsonProperty("method_signature")
    private String methodSignature;

    @JsonProperty("create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime create;
}