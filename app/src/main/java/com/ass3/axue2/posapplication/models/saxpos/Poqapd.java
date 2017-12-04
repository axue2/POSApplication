package com.ass3.axue2.posapplication.models.saxpos;

/**
 * Created by anthonyxue on 6/07/2017.
 * SAXPOS Order Items Table
 */

public class Poqapd {

    private String sInvoiceNo;
    private int nDetailNo;
    private String sCID;
    private String sItemID;
    private double nPrice;
    private int nInvoiceQty;
    private double nInvoiceAmt;
    private String sItemName;

    // Database Constants for Items
    public static final String TABLE_NAME = "poqapd";
    public static final String COLUMN_INVOICE_NO = "apd_invoice_no";
    public static final String COLUMN_DETAIL_NO = "apd_detail_no";
    // Not used
    public static final String COLUMN_CID = "apd_cid";
    public static final String COLUMN_ITEM_ID = "apd_item_id";
    public static final String COLUMN_PRICE = "apd_price";
    public static final String COLUMN_INVOICE_QTY = "apd_invoice_qty";
    public static final String COLUMN_INVOICE_AMT = "apd_amt";
    public static final String COLUMN_NEW_ITEM_ID = "apd_new_item_iid";
    public static final String COLUMN_DEPARTMENT_ID = "apd_department_id";

    public Poqapd(String invoiceNo, int detailNo, String cID,
                  String itemID, double price, int invoiceQty,
                  double invoiceAmt, String itemName){
        sInvoiceNo = invoiceNo;
        nDetailNo = detailNo;
        sCID = cID;
        sItemID = itemID;
        nPrice = price;
        nInvoiceQty = invoiceQty;
        nInvoiceAmt = invoiceAmt;
        sItemName = itemName;
    }

    public Poqapd(){
        sInvoiceNo = "";
        nDetailNo = -1;
        sCID = "CASH SALE";
        sItemID = "";
        nPrice = 0;
        nInvoiceQty = 0;
        nInvoiceAmt = 0;
        sItemName = "";
    }

    public String getsInvoiceNo() {
        return sInvoiceNo;
    }

    public void setsInvoiceNo(String sInvoiceNo) {
        this.sInvoiceNo = sInvoiceNo;
    }

    public int getnDetailNo() {
        return nDetailNo;
    }

    public void setnDetailNo(int nDetailNo) {
        this.nDetailNo = nDetailNo;
    }

    public String getsCID() {
        return sCID;
    }

    public void setsCID(String sCID) {
        this.sCID = sCID;
    }

    public String getsItemID() {
        return sItemID;
    }

    public void setsItemID(String sItemID) {
        this.sItemID = sItemID;
    }

    public double getnPrice() {
        return nPrice;
    }

    public void setnPrice(double nPrice) {
        this.nPrice = nPrice;
    }

    public int getnInvoiceQty() {
        return nInvoiceQty;
    }

    public void setnInvoiceQty(int nInvoiceQty) {
        this.nInvoiceQty = nInvoiceQty;
    }

    public double getnInvoiceAmt() {
        return nInvoiceAmt;
    }

    public void setnInvoiceAmt(double nInvoiceAmt) {
        this.nInvoiceAmt = nInvoiceAmt;
    }

    public String getsItemName() {
        return sItemName;
    }

    public void setsItemName(String sItemName) {
        this.sItemName = sItemName;
    }
}
