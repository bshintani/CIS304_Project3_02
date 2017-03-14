/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cis304_project3;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Bryan
 */
public class CarFaxDB {
    
    private static Connection con;
    
    public static void dbConnect() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/CarFaxDB");
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Failed to connect to DB\n" + sqlEx.getMessage());
        }
    }
    
    public static void dbClose() {
        try {
            con.close();
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Failed to close DB\n" + sqlEx.getMessage());
        }
    }
    
    public static void add(Car aCar) {
        try {
            String sql = "INSERT INTO CARS (VIN, MAKE, MODEL, YEARS) VALUES ('" +
                    aCar.getVin() + "','" +
                    aCar.getMake() + "','" +
                    aCar.getModel() + "'," +
                    aCar.getYear() + ")";
            
            Statement st = con.createStatement();
            st.execute(sql);
            
            JOptionPane.showMessageDialog(null, aCar.getYear() + " " +
                    aCar.getMake() + " " + 
                    aCar.getModel() + " was added.");
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Failed to add car\n" + sqlEx.getMessage());
        }
    }
    
    public static void delete(String aVin) {
        try {
            String sql = "DELETE FROM CARS WHERE VIN = '" + aVin + "'";
            
            Statement st = con.createStatement();
            st.execute(sql);
            
            JOptionPane.showMessageDialog(null, "Car has been deleted.");
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Failed to delete car.");
        }
    }
    
    public static void update(Car aCar) {
        try {
            String sql = "UPDATE CARS SET MAKE = ?, MODEL = ?, YEARS = ? WHERE VIN = ?"; 
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, aCar.getMake());
            ps.setString(2, aCar.getModel());
            ps.setInt(3, aCar.getYear());
            ps.setString(4, aCar.getVin());
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Update successful.");
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Failed to update.");
        }
    }
    
    public static Car select(String aVin) {
        Car aCar = null;
        
        try {
            String sql = "SELECT * FROM CARS WHERE VIN = '" + aVin + "'";
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                aCar = new Car(rs.getString("VIN"), rs.getString("MAKE"),
                rs.getString("MODEL"), rs.getInt("YEARS"));
                
                JOptionPane.showMessageDialog(null, "FOUND RECORD:\n" +
                        "VIN: " + aCar.getVin() + "\n" +
                        "Make: " + aCar.getMake() + "\n" + 
                        "Model: " + aCar.getModel() + "\n" +
                        "Year: " + aCar.getYear());
            } else {
                JOptionPane.showMessageDialog(null, "Failed to find record.");
            }
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Record was not selected.\n" + sqlEx.getMessage());       
        }
        
        return aCar;
    }
    
    public static int rowCount() {
        int size = 0;
        try {
            Statement st = con.createStatement();
            String sql = "SELECT COUNT(*) AS RECORDS FROM CARS";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                size = rs.getInt(1);
            }
            
            return size;
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Error loading record count\n\n" + sqlEx.getMessage());
        }
        return 0;
    }
}
