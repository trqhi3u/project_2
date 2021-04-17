/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import model.Status;
import Model.User;
import eproject2.connection.connectiondb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author Admin
 */
public class ControllUser {
    
        
        ArrayList<User> userList = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
    
        
    public ControllUser(){
        try {
            conn = new connectiondb().getConnection();
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ControllUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ResultSet getQueryResyl(String condition){
        try {
            String sql = "SELECT * FROM users WHERE " + condition;
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet getSearchUsersQueryResult(String searchTxt) {
        try {
            String query = "SELECT * FROM users WHERE fullname LIKE '%" + searchTxt + "%' OR location LIKE '%"  + searchTxt + "%' OR phone LIKE '%" + searchTxt + "%' OR role LIKE '%" + searchTxt + "%'" + "AND status = 'AVAILABLE'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public  ArrayList<User> finAll(ResultSet rs){
        try {
            userList = new ArrayList<>();
            while (rs.next()) {
                User us = new User();
                us.setId(rs.getInt("userID"));
                us.setFullname(rs.getString("fullname"));
                us.setLocation(rs.getString("location"));
                us.setPhone(rs.getString("phone"));
                us.setUsername(rs.getString("username"));
                us.setPassword(rs.getString("password"));
                us.setRole(rs.getString("role"));
                us.setStatus(Status.valueOf(rs.getString("status")));
                        
                                                                  
                userList.add(us);
            }
        } catch (Exception ex) {
            Logger.getLogger(ControllUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }
    public void insertUser(User user){
        try {
            String sql = "INSERT INTO users (fullname,location, phone, username, password, role,status) VALUES(?,?,?,?,?,?,?)";
            psmt = (PreparedStatement) conn.prepareStatement(sql);
            
            psmt.setString(1, user.getFullname());
            psmt.setString(2, user.getLocation());
            psmt.setString(3, user.getPhone());
            psmt.setString(4, user.getUsername());
            psmt.setString(5, user.getPassword());
            psmt.setString(6, user.getRole());
//            psmt.setString(7, user.getStatus());
            psmt.setString(7, user.getStatus().name());
            
            psmt.executeUpdate();
            if("ADMINISTRATOR".equals(user)){
                JOptionPane.showMessageDialog(null, "NEW ADMINISTRATOR ADDED");
            }else{
                JOptionPane.showMessageDialog(null, "NEW NORMAL USER ADDED");
            }            
        } catch (SQLException ex) {
             ex.printStackTrace();
        }
    }
    public void updateUser(User user){
        try {
            String sql = "UPDATE users SET fullname=?,location=?,phone=?,role=? WHERE username=?";
            psmt = (PreparedStatement) conn.prepareStatement(sql);
            
            psmt.setString(1, user.getFullname());
            psmt.setString(2, user.getLocation());
            psmt.setString(3, user.getPhone());
            psmt.setString(4, user.getRole());
            psmt.setString(5, user.getUsername());
            
            psmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Update successful");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void deleteUser(User user){
        try {
            String sql = "UPDATE users SET status = 'DELETED' WHERE username=?";
            psmt = (PreparedStatement) conn.prepareStatement(sql);
            
            psmt.setString(1, user.getUsername());
            
            psmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Delete Successfully!");
            
        } catch (Exception ex) {
             Logger.getLogger(ControllUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void changePass(User user){
        try {
                      
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            
            psmt = (PreparedStatement) conn.prepareStatement(sql);
            
            psmt.setString(1, user.getPassword());
            psmt.setString(2, user.getUsername());
            
            psmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Update Password Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void checkPass(User user, String userName ){
        try {
                      
            String sql = "select * form users where username = '"+userName+"'";
            
//            rs = stmt.executeQuery(sql);
            psmt = (PreparedStatement) conn.prepareStatement(sql);
            
//            psmt.setString(1, user.getPassword());
            psmt.setString(1, user.getUsername());
            
            psmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
