package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PetInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set the Content-Type header to "text/html"
        response.setContentType("text/html");

        String username = request.getParameter("username");

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

            // Retrieve the pet name for the given username from the "pets" table
            PreparedStatement statement = connection.prepareStatement("SELECT petname FROM pets WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // If a pet name is found, display it on the page
            if (resultSet.next()) {
                String petname = resultSet.getString("petname");
                response.getWriter().println("The pet name for " + username + " is " + petname);
            } else {
                response.getWriter().println("No pet found for " + username);
            }

            // Close the database connection
            connection.close();

            // Add a button to return to the index page
            response.getWriter().println("<br><button onclick=\"location.href='/'\">Return to Homepage</button>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}