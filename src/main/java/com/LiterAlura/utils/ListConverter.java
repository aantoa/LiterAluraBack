package com.LiterAlura.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListConverter {
    public static <T, R> List<R> convertirLista(List<T> origen, Function<T, R> mapper) {
        return origen.stream().map(mapper).collect(Collectors.toList());
    }
}
