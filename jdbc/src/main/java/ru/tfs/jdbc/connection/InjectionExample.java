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
import java.sql.Statement;

public class InjectionExample {

    public static void main(String[] args) {
        try (
            Connection connection = DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);

        ) {

            String user = "John' or '1'='1";
            //String user = "John";


//            Statement statement = connection.createStatement();
//            String query = String.format("select id from USERS where username = '%s'", user);
//            ResultSet resultSet = statement.executeQuery(query);

            String sql = "select id from USERS where username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                System.out.println("id - " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
