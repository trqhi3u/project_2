/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Flynn
 */
public class Supplier {

    private int supplierID;
    private String supplierCode;
    private String supplierName;
    private String supplierLocation;
    private String supplierContact;

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierLocation() {
        return supplierLocation;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierLocation(String supplierLocation) {
        this.supplierLocation = supplierLocation;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

}
