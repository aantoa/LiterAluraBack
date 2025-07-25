package com.LiterAlura.utils;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
