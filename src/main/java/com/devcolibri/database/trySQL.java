package com.devcolibri.database;

import java.sql.*;

public class trySQL {
    public static void SQLquer() throws SQLException {
        String username = "root";
        String password = "root";
        String connectionURL = "jdbc:mysql://localhost:3306/mydbtry";
        try (Connection conn = DriverManager.getConnection(connectionURL, username, password);
            Statement statement = conn.createStatement();
        ){
            statement.executeUpdate("create table if not exists romalox (id MEDIUMINT not null auto_increment, name VARCHAR(30) not null, PRIMARY KEY(id));");
            statement.executeUpdate("insert into romalox (name) values ('roma')");
            statement.executeUpdate("insert into romalox set name = 'ROman'");
            ResultSet resultSet = statement.executeQuery("select * from romalox");
            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.println(resultSet.getString("name"));
            }
        }

    }
}
