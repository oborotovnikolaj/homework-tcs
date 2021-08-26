package ru.tfs.jdbc.connection;

import static ru.tfs.jdbc.Constants.DATABASE;
import static ru.tfs.jdbc.Constants.PASSWORD;
import static ru.tfs.jdbc.Constants.URL;
import static ru.tfs.jdbc.Constants.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionExample {

    public static void main(String[] args) {
        String sql = "select e.id, e.name, d.name "
            + "from employees e "
            + "join departments d on e.department_id = e.id "
            + "where d.id = ?";

        try (
            Connection connection = DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, 101L);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                long id = resultSet.getLong(1);
                String department = resultSet.getString(3);
                System.out.println("id - " + id + "; name - " + name + "; department - " + department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
