package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/addstudent")
public class addstudent extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/login_schema",
                    "root",
                    "admin"
            );

            String sql = "INSERT INTO student(id, name, email) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, email);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<h2>Student Added Successfully</h2>");
            } else {
                out.println("<h2>Student Not Added</h2>");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}