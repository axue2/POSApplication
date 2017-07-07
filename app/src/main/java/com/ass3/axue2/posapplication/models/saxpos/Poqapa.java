package com.ass3.axue2.posapplication.models.saxpos;

/**
 * Created by anthonyxue on 6/07/2017.
 * SAXPOS Order & Delivery Table
 */

public class Poqapa {

    // Database Constants for Order
    public static final String TABLE_NAME = "poqapa";
    public static final String COLUMN_ID = "apa_invoice_no";
    public static final String COLUMN_TABLE_ID = "";
    public static final String COLUMN_CUSTOMER_ID = "apa_cid";
    public static final String COLUMN_CID_LINE = "apa_cid_line";
    public static final String COLUMN_DEPARTMENT_ID = "apa_department_id";
    public static final String COLUMN_ORDER_NO = "apa_order_no";
    public static final String COLUMN_INVOICE_DATE = "apa_invoice_date";
    public static final String COLUMN_TOT_INVOICE_AMT = "apa_tot_invoice_amt";
    public static final String COLUMN_TOT_DISCOUNT_AMT = "apa_tot_discount_amt";
    public static final String COLUMN_TOT_INVOICE_BAL = "apa_tot_invoice_bal";
    public static final String COLUMN_THIS_PAY_STATUS = "apa_this_pay_status";
    public static final String COLUMN_THIS_PAY_AMOUNT = "apa_this_pay_amount";
    public static final String COLUMN_THIS_PAY_BALANCE = "apa_this_pay_balance";
    // No users therefore no authorisor atm
    public static final String COLUMN_AUTHORISOR = "apa_authorisor";
    public static final String COLUMN_AUTHORISOR_DATE = "apa_authorisor_date";


    // Database constants for Delivery


    private String sID;
    private String sTableID;
    private String sCIDLine;
    private String sDepartmentID;
    private String sOrderNo;
    private String sInvoiceDate;
    private double sTotInvoiceAmt;
    private String sTotDiscountAmt;
    private String sTotInvoiceBal;
    private String sThisPayStatus;
    private String sThisPayAmount;
    private String sThisPayBalance;
    private String sAuthorisor;
    private String sAuthorisorDate;

    public Poqapa(String id, String tableID, String orderNo,
                  String invoiceDate, double totInvoiceAmt,
                  String totDiscountAmt, String totInvoiceBal,
                  String thisPayStatus, String thisPayAmount,
                  String thisPayBalance, String authorisor,
                  String authorisorDate){
        sID = id;
        sTableID = tableID;
        sCIDLine = "0000";
        sDepartmentID = "0003";
        sOrderNo = orderNo;
        sInvoiceDate = invoiceDate;
        sTotInvoiceAmt = totInvoiceAmt;
        sTotDiscountAmt = totDiscountAmt;
        sTotInvoiceBal = totInvoiceBal;
        sThisPayStatus = thisPayStatus;
        sThisPayAmount = thisPayAmount;
        sThisPayBalance = thisPayBalance;
        sAuthorisor = authorisor;
        sAuthorisorDate = authorisorDate;
    }


    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsTableID() {
        return sTableID;
    }

    public void setsTableID(String sTableID) {
        this.sTableID = sTableID;
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

    public String getsOrderNo() {
        return sOrderNo;
    }

    public void setsOrderNo(String sOrderNo) {
        this.sOrderNo = sOrderNo;
    }

    public String getsInvoiceDate() {
        return sInvoiceDate;
    }

    public void setsInvoiceDate(String sInvoiceDate) {
        this.sInvoiceDate = sInvoiceDate;
    }

    public double getsTotInvoiceAmt() {
        return sTotInvoiceAmt;
    }

    public void setsTotInvoiceAmt(double sTotInvoiceAmt) {
        this.sTotInvoiceAmt = sTotInvoiceAmt;
    }

    public String getsTotDiscountAmt() {
        return sTotDiscountAmt;
    }

    public void setsTotDiscountAmt(String sTotDiscountAmt) {
        this.sTotDiscountAmt = sTotDiscountAmt;
    }

    public String getsTotInvoiceBal() {
        return sTotInvoiceBal;
    }

    public void setsTotInvoiceBal(String sTotInvoiceBal) {
        this.sTotInvoiceBal = sTotInvoiceBal;
    }

    public String getsThisPayStatus() {
        return sThisPayStatus;
    }

    public void setsThisPayStatus(String sThisPayStatus) {
        this.sThisPayStatus = sThisPayStatus;
    }

    public String getsThisPayAmount() {
        return sThisPayAmount;
    }

    public void setsThisPayAmount(String sThisPayAmount) {
        this.sThisPayAmount = sThisPayAmount;
    }

    public String getsThisPayBalance() {
        return sThisPayBalance;
    }

    public void setsThisPayBalance(String sThisPayBalance) {
        this.sThisPayBalance = sThisPayBalance;
    }

    public String getsAuthorisor() {
        return sAuthorisor;
    }

    public void setsAuthorisor(String sAuthorisor) {
        this.sAuthorisor = sAuthorisor;
    }

    public String getsAuthorisorDate() {
        return sAuthorisorDate;
    }

    public void setsAuthorisorDate(String sAuthorisorDate) {
        this.sAuthorisorDate = sAuthorisorDate;
    }


}
