/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import eproject2.connection.connectiondb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Supplier;

/**
 *
 * @author Flynn
 */
public class SupplierDAO {

    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs1 = null;
    Statement stmt1 = null;
    ResultSet rs = null;
    ArrayList<Supplier> suppliers;

    public SupplierDAO() {
        try {
            con = new connectiondb().getConnection();
            stmt = con.createStatement();
            stmt1 = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getQueryResult(String condition) {
        try {
            String query = "SELECT * FROM suppliers " + "WHERE " + condition;
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ArrayList<Supplier> convertToArrayList(ResultSet rs) {
        try {
            suppliers = new ArrayList<>();
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierID(rs.getInt("supplierID"));
                supplier.setSupplierCode(rs.getString("supplierCode"));
                supplier.setSupplierContact(rs.getString("supplierContact"));
                supplier.setSupplierLocation(rs.getString("supplierLocation"));
                supplier.setSupplierName(rs.getString("supplierName"));
                suppliers.add(supplier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return suppliers;
    }

    public void add(Supplier supplier) {
        try {
            String query = "SELECT * FROM suppliers WHERE supplierName='" + supplier.getSupplierName() + "' AND supplierCode='" + supplier.getSupplierCode() + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "This supplier existed!");
            } else {
                String q = "INSERT INTO suppliers VALUES(null,?,?,?,?)";
                pstmt = (PreparedStatement) con.prepareStatement(q);
                pstmt.setString(1, supplier.getSupplierName());
                pstmt.setString(2, supplier.getSupplierCode());
                pstmt.setString(3, supplier.getSupplierContact());
                pstmt.setString(4, supplier.getSupplierLocation());

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Inserted Successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void update(Supplier supplier) {
        try {
            String query = "UPDATE suppliers SET supplierName=?,supplierCode=?,supplierContact=?,supplierLocation=? WHERE supplierID=?";
            pstmt = (PreparedStatement) con.prepareStatement(query);
            pstmt.setString(1, supplier.getSupplierName());
            pstmt.setString(2, supplier.getSupplierCode());
            pstmt.setString(3, supplier.getSupplierContact());
            pstmt.setString(4, supplier.getSupplierLocation());
            pstmt.setInt(5, supplier.getSupplierID());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ResultSet getSearchQueryResult(String searchTxt) {
        try {
            String query = "SELECT * FROM suppliers WHERE supplierName LIKE '%" + searchTxt + "%' OR supplierCode LIKE '%" + searchTxt + "%' OR supplierContact LIKE '%" + searchTxt + "%'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
