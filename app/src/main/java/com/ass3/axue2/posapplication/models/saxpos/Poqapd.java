package com.ass3.axue2.posapplication.models.saxpos;

/**
 * Created by anthonyxue on 6/07/2017.
 * SAXPOS Order Items Table
 */

public class Poqapd {

    private String sCID;
    private String sItemID;
    private String sPrice;
    private String sInvoiceQty;
    private String sInvoiceAmt;

    // Database Constants for Items
    public static final String TABLE_NAME = "poqapd";
    public static final String COLUMN_INVOICE_NO = "apd_invoice_no";
    public static final String COLUMN_DETAIL_NO = "apd_detail_no";
    // Set to 0000
    public static final String COLUMN_CID = "apd_cid";
    public static final String COLUMN_ITEM_ID = "apd_item_id";
    public static final String COLUMN_PRICE = "apd_price";
    public static final String COLUMN_INVOICE_QTY = "apd_invoice_qty";
    public static final String COLUMN_INVOICE_AMT = "apd_amt";
}
