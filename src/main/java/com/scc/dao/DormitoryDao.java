package com.scc.dao;

import com.scc.model.DormitoryEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DormitoryDao {

    // Constructor automatically creates the table when the DAO is created
    public DormitoryDao() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS allocations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_name TEXT NOT NULL, " +
                "building TEXT NOT NULL, " +
                "room_no TEXT NOT NULL, " +
                "monthly_rent REAL NOT NULL" +
                ");";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public boolean addAllocation(String studentName, String building, String roomNo, double monthlyRent) {
        String query = "INSERT INTO allocations (student_name, building, room_no, monthly_rent) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentName);
            pstmt.setString(2, building);
            pstmt.setString(3, roomNo);
            pstmt.setDouble(4, monthlyRent);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error saving allocation: " + e.getMessage());
            return false;
        }
    }

    public List<DormitoryEntity> getAllAllocations() {
        List<DormitoryEntity> allocations = new ArrayList<>();
        String query = "SELECT * FROM allocations";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                allocations.add(new DormitoryEntity(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("building"),
                        rs.getString("room_no"),
                        rs.getDouble("monthly_rent")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching allocations: " + e.getMessage());
        }
        return allocations;
    }
}