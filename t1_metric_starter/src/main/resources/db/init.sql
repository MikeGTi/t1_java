-- liquibase formatted sql
-- changeset init
--CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SEQUENCE IF NOT EXISTS data_source_error_log_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS data_source_error_log (
   id BIGINT NOT NULL,
   CONSTRAINT pk_data_source_error_log PRIMARY KEY (id),
   stack_trace TEXT,
   message TEXT,
   method_signature VARCHAR(255),
   "create" TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_create ON data_source_error_log ("create");