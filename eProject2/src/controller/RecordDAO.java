/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.User;
import com.google.gson.Gson;
import eproject2.connection.connectiondb;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Product;
import model.Record;
import model.RecordDetail;
import model.RecordType;
import model.Supplier;
import model.Customer;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.html.HTMLObjectElement;

/**
 *
 * @author Flynn
 */
public class RecordDAO {

    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs1 = null;
    Statement stmt1 = null;
    ResultSet rs = null;
    ArrayList<RecordDetail> recordDetails;
    Record record;
    ArrayList<Record> records;
    Supplier supplier;
    Customer customer;
    ArrayList<Product> products;

    public RecordDAO() {
        try {
            con = new connectiondb().getConnection();
            stmt = con.createStatement();
            stmt1 = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getInfo(String table, String field, String condition) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + table + " " + condition;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(rs.getString(field));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void addRecordDetail(RecordDetail recordDetail) {
        try {
            String q = "INSERT INTO recordDetail VALUES(?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(q);
            pstmt.setInt(1, recordDetail.getProductID());
            pstmt.setInt(2, recordDetail.getQuantity());
            pstmt.setInt(3, recordDetail.getRecordID());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addRecord(Record record, ArrayList<RecordDetail> recordDetails) {
        try {
            record.setRecordCode("RC" + Integer.toString(new RecordDAO().getInfo("records", "recordID", "").size() + 1));
            String q = "INSERT INTO records VALUES(null,?,?,?,?,?,?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(q);
            pstmt.setString(1, record.getRecordCode());
            pstmt.setString(2, record.getRecordType().toString());
            pstmt.setInt(3, record.getSupplierID());
            pstmt.setInt(4, record.getCustomerID());
            pstmt.setInt(5, record.getHandleBy());
            pstmt.setString(6, record.getDate());
            pstmt.setFloat(7, record.getTotalPrice() * (100 + record.getVat()) / 100);
            pstmt.setInt(8, record.getVat());

            pstmt.executeUpdate();

            String recordID = new RecordDAO().getInfo("records", "recordID", "WHERE recordCode = '" + record.getRecordCode() + "'").get(0);
            for (RecordDetail recordDetail : recordDetails) {
                recordDetail.setRecordID(Integer.parseInt(recordID));
                addRecordDetail(recordDetail);
                new CurrentStockDAO().updateCurrentStock(recordDetail.getProductID(), recordDetail.getQuantity(), record.getRecordType());
                new ProductDAO().soldOut(recordDetail.getProductID());
               
            }
            JOptionPane.showMessageDialog(null, "Successfully!");
        } catch (SQLException ex) {
            Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Record getSearchRecordQueryResult(String searchTxt) {
        try {
            String query = "SELECT * FROM records WHERE recordID = '" + searchTxt + "' OR recordCode = '" + searchTxt + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                record = new Record();
                record.setCustomerID(rs.getInt("customerID"));
                record.setDate(rs.getString("date"));
                record.setSupplierID(rs.getInt("supplierID"));
                record.setHandleBy(rs.getInt("handleBy"));
                record.setRecordCode(rs.getString("recordCode"));
                record.setRecordID(rs.getInt("recordID"));
                record.setRecordType(RecordType.valueOf(rs.getString("recordType")));
                record.setVat(rs.getInt("vat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public ArrayList<Record> findAll(String condition) {
        try {
            String query = "SELECT * FROM `records`" + condition + "ORDER BY `date` DESC ";
            rs = stmt.executeQuery(query);
            records = new ArrayList<>();
            while (rs.next()) {
                record = new Record();
                record.setCustomerID(rs.getInt("customerID"));
                record.setDate(rs.getString("date"));
                record.setSupplierID(rs.getInt("supplierID"));
                record.setHandleBy(rs.getInt("handleBy"));
                record.setRecordCode(rs.getString("recordCode"));
                record.setRecordID(rs.getInt("recordID"));
                record.setRecordType(RecordType.valueOf(rs.getString("recordType")));
                record.setVat(rs.getInt("vat"));
                record.setTotalPrice((float) rs.getDouble("totalPrice"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return records;
    }

    public ArrayList<RecordDetail> getSearchRecordDetailQueryResult(Record record) {
        try {
            String query = "SELECT * FROM recordDetail WHERE recordID = '" + record.getRecordID() + "'";
            rs = stmt.executeQuery(query);
            recordDetails = new ArrayList<>();
            while (rs.next()) {
                RecordDetail rcdt = new RecordDetail();
                rcdt.setProductID(rs.getInt("productID"));
                rcdt.setQuantity(Integer.parseInt(rs.getString("quantity")));
                rcdt.setRecordID(Integer.parseInt(rs.getString("recordID")));
                recordDetails.add(rcdt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return recordDetails;
    }

    public void deleteRecord(Record record) {
        try {
            String query = "UPDATE records SET recordType = 'DELETED' WHERE recordID=?";
            pstmt = (PreparedStatement) con.prepareStatement(query);
            pstmt.setInt(1, record.getRecordID());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Delete Successfully!");
        } catch (SQLException ex) {
            Logger.getLogger(RecordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void printInvoice(JPanel panel) {
//        PrinterJob printerJob = PrinterJob.getPrinterJob();
//        printerJob.setJobName("Print Record");
//        printerJob.setPrintable(new Printable() {
//            @Override
//            public int print(Graphics grphcs, PageFormat pf, int i) throws PrinterException {
//                if (i > 0) {
//                    return Printable.NO_SUCH_PAGE;
//                }
//                Graphics2D graphics2D = (Graphics2D) grphcs;
//                graphics2D.translate(pf.getImageableX() * 2, pf.getImageableY() * 2);
//                graphics2D.scale(0.5, 0.5);
//                panel.paint(graphics2D);
//                return Printable.PAGE_EXISTS;
//            }
//        });
//        boolean returnResult = printerJob.printDialog();
//        if (returnResult) {
//            try {
//                printerJob.print();
//            } catch (PrinterException e) {
//                JOptionPane.showMessageDialog(null, "Print error: " + e.getMessage());
//            }
//        }
//    }
    public void printInvoice(Record record) {
        try {
            File recordTemplateFile = null;
            String htmlString = "";
            String recordCode = record.getRecordCode();
            String date = record.getDate();
            User user = new Controller.ControllUser().finAll(new Controller.ControllUser().getQueryResyl("userID = '" + record.getHandleBy() + "'")).get(0);
            String handleBy = user.getUsername();
            recordDetails = new RecordDAO().getSearchRecordDetailQueryResult(record);
            products = new ProductDAO().convertToArrayList(new ProductDAO().getQueryResult("1"));
            String rcdtsJson = new Gson().toJson(recordDetails);
            String recordJson = new Gson().toJson(record);
            String prdJson = new Gson().toJson(products);
            String subTotal = Float.toString(record.getTotalPrice());
            String tax = Float.toString(record.getTotalPrice() * record.getVat() / 100);
            String total = Float.toString(record.getTotalPrice() * (record.getVat() + 100) / 100);
            if (record.getRecordType() == RecordType.IMPORT) {
                recordTemplateFile = new File("D:\\Flynn\\java\\prj\\eproject2.github.io\\eProject2\\src\\View\\recordTemplate.html");
                htmlString = FileUtils.readFileToString(recordTemplateFile);
                supplier = new SupplierDAO().convertToArrayList(new SupplierDAO().getQueryResult("supplierID ='" + record.getSupplierID() + "'")).get(0);
                String supplierName = supplier.getSupplierName();
                String supplierLocation = supplier.getSupplierLocation();
                String supplierContact = supplier.getSupplierContact();
                htmlString = htmlString.replace("$supplierName", supplierName);
                htmlString = htmlString.replace("$supplierLocation", supplierLocation);
                htmlString = htmlString.replace("$supplierContact", supplierContact);
            } else if (record.getRecordType() == RecordType.EXPORT) {
                recordTemplateFile = new File("D:\\Flynn\\java\\prj\\eproject2.github.io\\eProject2\\src\\View\\recordTemplate-sale.html");
                htmlString = FileUtils.readFileToString(recordTemplateFile);
                customer = new CustomerDAO().convertToArrayList(new CustomerDAO().getQueryResult("customerID ='" + record.getCustomerID() + "'")).get(0);
                String customerName = customer.getCustomerName();
                String customerAddress = customer.getCustomerAddress();
                String customerPhone = customer.getCustomerPhone();
                htmlString = htmlString.replace("$customerName", customerName);
                htmlString = htmlString.replace("$customerAddress", customerAddress);
                htmlString = htmlString.replace("$customerPhone", customerPhone);
            }
            

            htmlString = htmlString.replace("$recordCode", recordCode);
            htmlString = htmlString.replace("$date", date);
            htmlString = htmlString.replace("$handleBy", handleBy);
            htmlString = htmlString.replace("$rcdtsJson", rcdtsJson);
            htmlString = htmlString.replace("$recordJson", recordJson);
            htmlString = htmlString.replace("$prdJson", prdJson);
            htmlString = htmlString.replace("$subTotal", subTotal);
            htmlString = htmlString.replace("$tax", tax);
            htmlString = htmlString.replace("$total", total);

            File newHtmlFile = new File("D:\\Flynn\\java\\prj\\eproject2.github.io\\eProject2\\src\\printedRecord\\" + record.getRecordCode() + ".html");
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
            JOptionPane.showMessageDialog(null, "Printed record successfully");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Printed record failed");
        }

    }

    public ArrayList<Record> convertToArrayList(ResultSet rs) {
        try {
            records = new ArrayList<>();
            while (rs.next()) {
                Record record = new Record();
                record.setRecordID(Integer.parseInt(rs.getString("recordID")));
                record.setRecordCode(rs.getString("recodeCode"));
                record.setRecordType(RecordType.valueOf(rs.getString("recordType")));
                record.setSupplierID(Integer.parseInt(rs.getString("supplierID")));
                record.setCustomerID(Integer.parseInt(rs.getString("customerID")));
                record.setHandleBy(Integer.parseInt(rs.getString("handleBy")));
                record.setDate(rs.getString("date"));
                record.setTotalPrice((float) Double.parseDouble(rs.getString("totalPrice")));
                record.setVat(Integer.parseInt(rs.getString("vat")));
                records.add(record);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    public ArrayList<Integer> getSoldQuantity(Product prd) {
        ArrayList<Integer> list;
        list = new ArrayList<>();
        int quantity = 0, sum = 0;
        try {
            String query = "SELECT * FROM recordDetail WHERE productID = '" + prd.getProductID() + "' AND recordID IN (SELECT recordID FROM records WHERE recordType = 'EXPORT')";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                quantity += rs.getInt("quantity");
            }
            query = "SELECT SUM(quantity) FROM recordDetail WHERE recordID IN (SELECT recordID FROM records WHERE recordType = 'EXPORT')";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                sum = rs.getInt("SUM(quantity)");
            }
            list.add(quantity);
            list.add(sum);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
