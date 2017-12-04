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
                    System.out.println("poqapaToOrder: " + poqapa.getsOrderNo());
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

/*
            switch (poqapa.getsThisPayStatus()){

                // Paid
                case "P":
                    order.setsType(Order.TYPE_EAT_IN);
                    order.setsStatus(Order.STATUS_PAID);
                    // Eat-in Order
                case "1":
                    order.setsType(Order.TYPE_EAT_IN);
                    order.setsStatus(Order.STATUS_UNPAID);
                    try {
                        order.setnTableID(Long.parseLong(poqapa.getsOrderNo()));
                    }catch (NumberFormatException e){
                        System.out.println("poqapaToOrder: " + poqapa.getsOrderNo());
                    }

                    // Booking
                case "2":
                    order.setsType(Order.TYPE_BOOKING);
                    order.setsStatus(Order.STATUS_UNPAID);

                    // Takeaway
                case "3":
                    order.setsType(Order.TYPE_TAKEAWAY);
                    order.setsStatus(Order.STATUS_UNPAID);

                    // Delivery
                case "4":
                    order.setsType(Order.TYPE_DELIVERY);
                    order.setsStatus(Order.STATUS_UNPAID);

                default:
                    order.setsType(Order.TYPE_EAT_IN);
                    order.setsStatus(Order.STATUS_PAID);
            }
*/
            order.setnTotal(poqapa.getsTotInvoiceAmt());
        }

        return new Order();
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
            //orderItem.setnOrderItemID(Long.parseLong(poqapd.getsInvoiceNo() + String.valueOf(poqapd.getnDetailNo())));
        }catch (NumberFormatException e){
            orderItem.setnOrderID(-1);
            orderItem.setnOrderItemID(-1);
        }

        //System.out.println(poqapd.getsInvoiceNo() + ", " + poqapd.getnDetailNo());
        //System.out.println("poqapdToOrderItem: " + orderItem.getnOrderItemID());
        //System.out.println("orderItem: " + poqapd.getsInvoiceNo() + ", Position: " + poqapd.getnDetailNo());
        orderItem.setnPosition(poqapd.getnDetailNo());
        orderItem.setnPrice(poqapd.getnPrice());
        orderItem.setnQuantity(poqapd.getnInvoiceQty());
        orderItem.setnProductID(Long.valueOf(poqapd.getsItemID()));
        orderItem.setsProductName(poqapd.getsItemName());

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
        //System.out.println("Position: " + orderItem.getnPosition());
        poqapd.setsInvoiceNo(convertToDigits((int) orderItem.getnOrderID(), 7));
        poqapd.setsItemID(convertToDigits((int) orderItem.getnProductID(),6));
        poqapd.setnDetailNo(orderItem.getnPosition());
        poqapd.setnPrice(orderItem.getnPrice());
        poqapd.setnInvoiceQty(orderItem.getnQuantity());
        poqapd.setsItemName(orderItem.getsProductName());


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
