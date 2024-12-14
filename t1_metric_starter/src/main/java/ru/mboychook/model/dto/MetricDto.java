package ru.mboychook.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.mboychook.model.entity.Metric;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Metric}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricDto implements Serializable {

    private Long id;

    @JsonProperty("execution_time")
    private Long executionTime;

    @JsonProperty("method_name")
    private String methodName;

    @JsonProperty("method_args")
    private String methodArgs;

    @JsonProperty("create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime create;
}