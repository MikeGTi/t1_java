package ru.mboychook.service;

public interface ErrorLogService {

    void saveErrorInfo(Exception e, String methodSignature);

    void sendError(Exception e, String methodSignature);
}