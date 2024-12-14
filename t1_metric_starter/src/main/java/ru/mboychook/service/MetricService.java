package ru.mboychook.service;


public interface MetricService<T> {
    void send(T object);
}