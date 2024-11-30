package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_source_error_log")
public class DataSourceErrorLog {

    @Id
    @SequenceGenerator(name = "data_source_error_log_generator", sequenceName = "data_source_error_log_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data_source_error_log_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "trace")
    private String trace;

    @Column(name = "message")
    private String errorMessage;

    @Column(name = "method_signature")
    private String methodSignature;

    public void setMessage(String anyTypeError) {
        this.errorMessage = anyTypeError;
    }

    public void setStackTrace(String exceptionStackTrace) {
        this.trace = exceptionStackTrace;
    }

    @Override
    public String toString() {
        return "DataSourceErrorLog{" +
                "id=" + id +
                ", trace='" + trace + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", methodSignature='" + methodSignature + '\'' +
                '}';
    }
}
