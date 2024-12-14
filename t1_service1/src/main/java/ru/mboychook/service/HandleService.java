package ru.mboychook.service;

public interface HandleService<T> {
    void handle(Iterable<T> entities);
}
