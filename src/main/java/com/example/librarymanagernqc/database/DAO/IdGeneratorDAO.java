package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.database.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdGeneratorDAO {
    public int getNextId() {
        int nextid = 0;
        String query = "SELECT * FROM IdGenerator";

        try (Statement statement = DatabaseHelper.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                nextid = resultSet.getInt("nextId");
            }

            return nextid;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public boolean updateNextId(int id) {
        String query = "UPDATE IdGenerator SET nextId = ? WHERE finalId = 1";
        try (PreparedStatement statement = DatabaseHelper.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
