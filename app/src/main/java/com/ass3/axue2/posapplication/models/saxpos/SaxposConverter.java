package com.ass3.axue2.posapplication.models.saxpos;

import com.ass3.axue2.posapplication.models.operational.Customer;
import com.ass3.axue2.posapplication.models.operational.Group;
import com.ass3.axue2.posapplication.models.operational.Order;
import com.ass3.axue2.posapplication.models.operational.OrderItem;
import com.ass3.axue2.posapplication.models.operational.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyxue on 6/07/2017.
 */

public class SaxposConverter {

    public Customer popcumToCustomer(){
        return new Customer();
    }

    public Order popapaToOrder(Poqapa poqapa){
        Order order = new Order();
        order.setnOrderID(Long.parseLong(poqapa.getsID()));
        order.setnTotal(poqapa.getsTotInvoiceAmt());
/*        order.setsStatus();
        order.setsType();*/


        return new Order();
    }

    public OrderItem popapdToOrderItem(){

        return new OrderItem();
    }

    public static List<Group> stkcatToGroup(List<Stkcat> list){
        List<Group> groups = new ArrayList<>();
        for (Stkcat stkcat : list){
            Group group = new Group();
            group.setnGroupID(Long.parseLong(stkcat.getsID()));
            group.setsGroupName(stkcat.getsName());
            groups.add(group);
        }
        return groups;
    }

    public static List<Product> stkiteToProduct(List<Stkite> stkites){
        List<Product> products = new ArrayList<>();
        for (Stkite stkite : stkites ){
            // Check item is valid
            // Checks GroupBy to find misc item
            if (stkite.getsGroupBy() != null && stkite.getsStatus().equals("Y")) {
                Product product = new Product();
                product.setnProductID(Long.parseLong(stkite.getsID()));
                product.setnGroupID(Long.parseLong(stkite.getsGroupBy()));
                product.setsProductName(stkite.getsName());
                product.setnPrice(stkite.getsSalesPrice());
                products.add(product);
            }
        }

        return products;
    }




}
