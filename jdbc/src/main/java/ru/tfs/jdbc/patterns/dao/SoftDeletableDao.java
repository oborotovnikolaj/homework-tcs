package ru.tfs.jdbc.patterns.dao;

public interface SoftDeletableDao<T> extends VoDao<T> {
    void softDelete(T dao);
}
