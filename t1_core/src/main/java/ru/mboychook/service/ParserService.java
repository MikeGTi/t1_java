package ru.mboychook.service;

import java.io.IOException;
import java.util.List;

public interface ParserService<T> {
    List<T> parseJson() throws IOException;
}
