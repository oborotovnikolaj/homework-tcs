package ru.tfs.jdbc.patterns.dao;

import ru.tfs.jdbc.patterns.model.ClientVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface VoDao<T> {
    T getById(Long id);
    T save(T employee);
    void delete(T employeeVO);
}
