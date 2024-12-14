package ru.mboychook.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link MetricStatistic}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricStatisticDto implements Serializable {

    private Long id;

    @JsonProperty("execution_time")
    private Long executionTime;

    @JsonProperty("exceeded_on_time")
    private Long exceededOnTime;

    @JsonProperty("method_name")
    private String methodName;

    @JsonProperty("method_args")
    private String methodArgs;

}