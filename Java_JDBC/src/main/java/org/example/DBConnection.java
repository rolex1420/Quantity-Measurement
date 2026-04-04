package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private static volatile Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "Test_123");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            synchronized (DBConnection.class) {
                if (connection != null) {
                    try {
                        connection.close();
                        System.out.println("The connection is closed successfully");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Error in closing connection: " + e);
                    }
                }
                connection = null;
            }
        }
    }

    public static void createTable(String tableName) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                st.execute("CREATE TABLE " + tableName + " (id INT PRIMARY KEY AUTO_INCREMENT, first_name VARCHAR(30), last_name VARCHAR(30), age INT)");
                System.out.println("Table: " + tableName + " created successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            getConnection();
            createTable(tableName);
        }
    }

    public static void insertPerson(Person person) {
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO person VALUES(null, ?, ?, ?)");
            st.setString(1, person.getFirst_name());
            st.setString(2, person.getLast_name());
            st.setInt(3, person.getAge());
            System.out.println("Rows affected: " + st.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> getAllPerson() {
        List<Person> ans = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM person");
            while (rs.next()) {
                String first_name = rs.getString(2);
                String last_name = rs.getString(3);
                int age = rs.getInt(4);
                ans.add(new Person(first_name, last_name, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static Person getPersonById(int id) {
        Person ans = null;
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM person WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String first_name = rs.getString(2);
                String last_name = rs.getString(3);
                int age = rs.getInt(4);
                ans = new Person(first_name, last_name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static void deletePersonById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement("DELETE FROM person WHERE id = ?");
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Person with id " + id + " has been successfully deleted");
            } else {
                System.out.println("No person found with id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void updatePerson(int id, Person updatedPerson) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE person SET first_name = ?, last_name = ?, age = ? WHERE id = ?"
            );
            st.setString(1, updatedPerson.getFirst_name());
            st.setString(2, updatedPerson.getLast_name());
            st.setInt(3, updatedPerson.getAge());
            st.setInt(4, id);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Person with id " + id + " has been successfully updated");
            } else {
                System.out.println("No person found with id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}