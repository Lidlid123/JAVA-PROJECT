package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String petname = request.getParameter("petname");

        try {
            // Load the application.properties file
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

            // Create a connection to the MySQL database using the properties
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));

            // Insert the user and pet data into the "pets" table
            PreparedStatement statement = connection.prepareStatement("INSERT INTO pets (username, petname) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, petname);
            statement.executeUpdate();

            // Close the database connection
            connection.close();

            // Redirect the user back to the index page
            response.sendRedirect(request.getContextPath() + "/");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}