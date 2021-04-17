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
public class RecordDetail {
    private int recordID;
    private int productID;
    private int quantity;

    public int getRecordID() {
        return recordID;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public RecordDetail(int recordID, int productID, int quantity) {
        this.recordID = recordID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public RecordDetail() {
    }
    
}
