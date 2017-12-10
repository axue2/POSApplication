package com.ass3.axue2.posapplication.models.saxpos;

import com.ass3.axue2.posapplication.models.operational.Customer;
import com.ass3.axue2.posapplication.models.operational.Group;
import com.ass3.axue2.posapplication.models.operational.Order;
import com.ass3.axue2.posapplication.models.operational.OrderItem;
import com.ass3.axue2.posapplication.models.operational.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by anthonyxue on 6/07/2017.
 *
 */

public class SaxposConverter {
    //TODO: List all Customers
    public static Customer popcumToCustomer(){
        return new Customer();
    }

    //TODO: Add date timer for orders in last 24 hrs?
    public static Order poqapaToOrder(Poqapa poqapa){
        Order order = new Order();
        // Do not allow Refunded Items
        try {
            order.setnOrderID(Long.parseLong(poqapa.getsID()));
        }catch (NumberFormatException e){
            order.setnOrderID(-1);
        }
        if (order.getnOrderID() > 0){

            order.setnTotal(poqapa.getsTotInvoiceAmt());

            if (poqapa.getsThisPayStatus().equals("P")) {
                order.setsType(Order.TYPE_EAT_IN);
                order.setsStatus(Order.STATUS_PAID);
            } else if (poqapa.getsThisPayStatus().equals("1")) {
                order.setsType(Order.TYPE_EAT_IN);
                order.setsStatus(Order.STATUS_UNPAID);
                try {
                    order.setnTableID(Long.parseLong(poqapa.getsOrderNo()));
                }catch (NumberFormatException e) {
                    order.setnTableID(-1);
                }
                try {
                    order.setnTillID(Long.parseLong(poqapa.getsTillID()));
                }catch (NumberFormatException e) {
                    order.setnTillID(0);
                }
            } else if (poqapa.getsThisPayStatus().equals("2")) {
                order.setsType(Order.TYPE_BOOKING);
                order.setsStatus(Order.STATUS_UNPAID);
            } else if (poqapa.getsThisPayStatus().equals("3")){
                order.setsType(Order.TYPE_TAKEAWAY);
                order.setsStatus(Order.STATUS_UNPAID);
            } else if (poqapa.getsThisPayStatus().equals("4")){
                order.setsType(Order.TYPE_DELIVERY);
                order.setsStatus(Order.STATUS_UNPAID);
            } else {
                order.setsType(Order.TYPE_EAT_IN);
                order.setsStatus(Order.STATUS_PAID);
            }

            order.setnTotal(poqapa.getsTotInvoiceAmt());
        }

        return order;
    }

    public static List<Order> poqapaToOrders(List<Poqapa> poqapas){
        List<Order> orders = new ArrayList<>();

        for (Poqapa poqapa : poqapas){
            orders.add(poqapaToOrder(poqapa));
        }

        return orders;
    }

    public static Poqapa orderToPoqapa(Order order){
        Poqapa poqapa = new Poqapa();
        poqapa.setsID(convertToDigits((int) order.getnOrderID(), 7));
        poqapa.setsOrderNo(String.valueOf(order.getnTableID()));
        poqapa.setsThisPayStatus(order.getsStatus());
        poqapa.setsTillID(String.valueOf(order.getnTillID()));
        poqapa.setsGuestNo(String.valueOf(order.getnGuests()));
        poqapa.setsNextTransaction(String.valueOf(0));

        if (order.getsStatus().equals(Order.STATUS_PAID)){
            poqapa.setsThisPayStatus("P");
        } else if (order.getsType().equals(Order.TYPE_EAT_IN)) {
            poqapa.setsThisPayStatus("1");
        } else if (order.getsType().equals(Order.TYPE_BOOKING)) {
            poqapa.setsThisPayStatus("2");
        } else if (order.getsType().equals(Order.TYPE_TAKEAWAY)) {
            poqapa.setsThisPayStatus("3");
        } else if (order.getsType().equals(Order.TYPE_DELIVERY)) {
            poqapa.setsThisPayStatus("4");
        } else {
            poqapa.setsThisPayStatus("1");
        }

        // Get current time
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        String timeStamp = df.format(Calendar.getInstance().getTime());
        poqapa.setsInvoiceDate(String.valueOf(timeStamp));
        poqapa.setsAuthorisorDate(String.valueOf(timeStamp));
        poqapa.setsTotInvoiceAmt(order.getnTotal());
        //TODO: Add Discounts
        poqapa.setsTotDiscountAmt("0");
        //TODO: Total invoice balance needs to reflect discounts
        poqapa.setsTotInvoiceBal(String.valueOf(order.getnTotal()));
        //TODO: Authorisor date to be set as user
        poqapa.setsAuthorisor("Mobile");

        return poqapa;
    }

    public static OrderItem poqapdToOrderItem(Poqapd poqapd){
        OrderItem orderItem = new OrderItem();

        try {
            orderItem.setnOrderID(Long.parseLong(poqapd.getsInvoiceNo()));
        }catch (NumberFormatException e){
            orderItem.setnOrderID(-1);
            orderItem.setnOrderItemID(-1);
        }
        try {
            orderItem.setnPrinterID(Long.parseLong(poqapd.getsPrinterID()));
        }catch (NumberFormatException e){
            orderItem.setnPrinterID(-1);
        }
        try {
            orderItem.setnPrinter2ID(Long.parseLong(poqapd.getsPrinter2ID()));
        }catch (NumberFormatException e){
            orderItem.setnPrinter2ID(-1);
        }
        try {
            orderItem.setnTillID(Long.parseLong(poqapd.getsTillID()));
        }catch (NumberFormatException e){
            orderItem.setnTillID(-1);
        }
        try {
            orderItem.setnProductID(Long.valueOf(poqapd.getsItemID()));
        }catch (NumberFormatException e){
            orderItem.setnProductID(-1);
        }
        try {
            orderItem.setnDepartmentID(Long.valueOf(poqapd.getsDepartmentID()));
        }catch (NumberFormatException e){
            orderItem.setnDepartmentID(-1);
        }
        orderItem.setnQuantityPrinted(poqapd.getnQuantityPrinted());
        orderItem.setnPosition(poqapd.getnDetailNo());
        orderItem.setnPrice(poqapd.getnPrice());
        orderItem.setnQuantity(poqapd.getnInvoiceQty());
        orderItem.setsProductName(poqapd.getsItemName());
        orderItem.setnGSTPercent(poqapd.getnGSTPercent());

        return orderItem;
    }

    public static List<OrderItem> poqapdToOrderItems(List<Poqapd> list){
        List<OrderItem> orderItems = new ArrayList<>();
        for (Poqapd poqapd : list){
            orderItems.add(poqapdToOrderItem(poqapd));
        }

        return orderItems;
    }

    public static Poqapd orderItemToPoqapd(OrderItem orderItem){
        Poqapd poqapd = new Poqapd();
        poqapd.setsInvoiceNo(convertToDigits((int) orderItem.getnOrderID(), 7));
        poqapd.setsItemID(convertToDigits((int) orderItem.getnProductID(),6));
        poqapd.setnDetailNo(orderItem.getnPosition());
        poqapd.setnPrice(orderItem.getnPrice());
        poqapd.setnInvoiceQty(orderItem.getnQuantity());
        poqapd.setsItemName(orderItem.getsProductName());
        poqapd.setsDepartmentID(String.valueOf(orderItem.getnDepartmentID()));
        poqapd.setsTillID(String.valueOf(orderItem.getnTillID()));
        poqapd.setnInvoiceAmt(orderItem.getnPrice() * orderItem.getnQuantity());

        // Calculate GST
        poqapd.setnGSTPercent(orderItem.getnGSTPercent());

        // TODO: Figure out what is causing GST percent to be 1
        if (poqapd.getnGSTPercent() >= 1) {
            poqapd.setnGSTPercent(-0.1);
        }
        System.out.println("Invoice Amt: " + poqapd.getnInvoiceAmt());
        System.out.println("GST Percent: " + poqapd.getnGSTPercent());
        double percent = 1 - poqapd.getnGSTPercent();
        System.out.println("Calculated Percentage: " + percent);
        poqapd.setnActualAmount(poqapd.getnInvoiceAmt() / percent);
        poqapd.setnGSTAmount(poqapd.getnInvoiceAmt() - poqapd.getnActualAmount());
        System.out.println("Actual Amount: " + poqapd.getnActualAmount());
        System.out.println("GST Amount: " + poqapd.getnGSTAmount());

        if (orderItem.getnPrinterID() > 0){
            poqapd.setsPrinterID(convertToDigits((int) orderItem.getnPrinterID(), 3));
        }
        if (orderItem.getnPrinter2ID() > 0){
            poqapd.setsPrinter2ID(convertToDigits((int) orderItem.getnPrinter2ID(), 3));
        }
        poqapd.setnQuantityPrinted(orderItem.getnQuantityPrinted());

        return poqapd;
    }

    public static List<Poqapd> orderItemToPoqapds(List<OrderItem> list){
        List<Poqapd> poqapds = new ArrayList<>();
        for (OrderItem orderItem : list){
            poqapds.add(orderItemToPoqapd(orderItem));
        }

        return poqapds;
    }


    public static Group stkcatToGroup(Stkcat stkcat){
        Group group = new Group();
        group.setnGroupID(Long.parseLong(stkcat.getsID()));
        group.setsGroupName(stkcat.getsName());
        return group;
    }

    public static List<Group> stkcatToGroups(List<Stkcat> list){
        List<Group> groups = new ArrayList<>();
        for (Stkcat stkcat : list){
            groups.add(stkcatToGroup(stkcat));
        }
        return groups;
    }

    public static Product stkiteToProduct(Stkite stkite){
        Product product = new Product();
        product.setnProductID(Long.parseLong(stkite.getsID()));
        product.setnGroupID(Long.parseLong(stkite.getsGroupBy()));
        product.setsProductName(stkite.getsName());
        product.setnPrice(stkite.getsSalesPrice());
        product.setnGSTPercent(stkite.getnGST());
        try {
            product.setnPrinterID(Long.parseLong(stkite.getsPrinter()));
        }catch (NumberFormatException e){
            product.setnPrinterID(0);
        }
        try {
            product.setnPrinter2ID(Long.parseLong(stkite.getsPrinter2()));
        }catch (NumberFormatException e){
            product.setnPrinter2ID(0);
        }
        return product;
    }

    public static List<Product> stkiteToProducts(List<Stkite> stkites){
        List<Product> products = new ArrayList<>();
        for (Stkite stkite : stkites ){
            // Check item is valid
            // Checks GroupBy to find misc item
            if (stkite.getsGroupBy() != null && stkite.getsStatus().equals("Y")) {
                Product product = stkiteToProduct(stkite);
                products.add(product);
            }
        }

        return products;
    }

    public static int countDigits(int x) {
        return (int) Math.log10(x) + 1;
    }
    // Converts a number with less than 7 digits to 7 digits
    public static String convertToDigits(int x, int digits) {
        System.out.println("x value: " + x);
        System.out.println("digits: " + digits);
        int noDigits = countDigits(x);
        int addDigits = digits - noDigits;
        String convertedValue = String.valueOf(x);
        while (addDigits > 0) {
            convertedValue = "0".concat(convertedValue);
            addDigits--;
        }
        System.out.println("Result: " + convertedValue);
        return convertedValue;
    }
}
