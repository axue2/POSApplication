package com.ass3.axue2.posapplication.models.saxpos;

/**
 * Created by anthonyxue on 6/07/2017.
 * SAXPOS Order Items Table
 */

public class Poqapd {

    private String sInvoiceNo;
    private int nDetailNo;
    private String sCID;
    private String sCIDLine;
    private String sItemID;
    private double nPrice;
    private int nInvoiceQty;
    private double nInvoiceAmt;
    private String sItemName;

    private String sDepartmentID;
    private String sTillID;
    private String sPrinterID;
    private String sPrinter2ID;
    private int nQuantityPrinted;
    private double nGSTPercent;
    private double nGSTAmount;
    private double nActualAmount;

    // Database Constants for Items
    public static final String TABLE_NAME = "poqapd";
    public static final String COLUMN_INVOICE_NO = "apd_invoice_no";
    public static final String COLUMN_DETAIL_NO = "apd_detail_no";
    public static final String COLUMN_CID = "apd_cid";
    public static final String COLUMN_CID_LINE = "apd_cid_line";
    public static final String COLUMN_ITEM_ID = "apd_item_id";
    public static final String COLUMN_PRICE = "apd_price";
    public static final String COLUMN_INVOICE_QTY = "apd_invoice_qty";
    public static final String COLUMN_INVOICE_AMT = "apd_amt";
    public static final String COLUMN_NEW_ITEM_ID = "apd_new_item_iid";
    public static final String COLUMN_DEPARTMENT_ID = "apd_department_id";
    public static final String COLUMN_NEW_ITEM_DES = "apd_new_item_des";
    //Till ID
    public static final String COLUMN_TITLE = "apd_title";
    // Printer IDs
    public static final String COLUMN_DF_TYPE = "apd_df_type";
    public static final String COLUMN_NEW_SUPPLIER_NAME = "apd_new_supplier_name";
    // Quantity Printed
    public static final String COLUMN_EXPENSE_TYPE = "apd_expense_type";

    public static final String COLUMN_GST_PERCENT = "apd_gst_percent";
    public static final String COLUMN_GST_AMT = "apd_gst_amt";
    public static final String COLUMN_ACTUAL_AMT = "apd_actual_amt";

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
        sCIDLine = "0000";
        sItemID = "";
        nPrice = 0;
        nInvoiceQty = 0;
        nInvoiceAmt = 0;
        sItemName = "";
        sDepartmentID = "";
        sTillID = "";
        sPrinterID = "";
        sPrinter2ID = "";
        nQuantityPrinted = 0;
        nGSTPercent = -0.1;
        nGSTPercent = 0;
        nActualAmount = 0;

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

    public String getsCIDLine() {
        return sCIDLine;
    }

    public void setsCIDLine(String sCIDLine) {
        this.sCIDLine = sCIDLine;
    }

    public String getsDepartmentID() {
        return sDepartmentID;
    }

    public void setsDepartmentID(String sDepartmentID) {
        this.sDepartmentID = sDepartmentID;
    }

    public String getsTillID() {
        return sTillID;
    }

    public void setsTillID(String sTillID) {
        this.sTillID = sTillID;
    }

    public String getsPrinterID() {
        return sPrinterID;
    }

    public void setsPrinterID(String sPrinterID) {
        this.sPrinterID = sPrinterID;
    }

    public String getsPrinter2ID() {
        return sPrinter2ID;
    }

    public void setsPrinter2ID(String sPrinter2ID) {
        this.sPrinter2ID = sPrinter2ID;
    }

    public int getnQuantityPrinted() {
        return nQuantityPrinted;
    }

    public void setnQuantityPrinted(int nQuantityPrinted) {
        this.nQuantityPrinted = nQuantityPrinted;
    }

    public double getnGSTPercent() {
        return nGSTPercent;
    }

    public void setnGSTPercent(double nGSTPercent) {
        this.nGSTPercent = nGSTPercent;
    }

    public double getnGSTAmount() {
        return nGSTAmount;
    }

    public void setnGSTAmount(double nGSTAmount) {
        this.nGSTAmount = nGSTAmount;
    }

    public double getnActualAmount() {
        return nActualAmount;
    }

    public void setnActualAmount(double nActualAmount) {
        this.nActualAmount = nActualAmount;
    }



}
