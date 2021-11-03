/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryp.dao;

import com.sg.flooringmasteryp.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;



/**
 *
 * @author JuanRussi
 */
public class OrdersDaoFileImplTest
{
    private OrdersDao ordersDao = new OrdersDaoFileImpl(dataFolder);
    
    private static String dataFolder = "src/test/resources/";

    @Test
    public void testAddGetOrders() throws Exception
    {
        LocalDate date = LocalDate.parse("20211014",
        DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Order> initialOrders = ordersDao.getOrders(date);

        Order order = new Order();
        order.setDate(date);
        order.setCustomerName("Zzzz Name");
        order.setState("NY");
        order.setTaxRate(new BigDecimal("3.00"));
        order.setProductType("Corrugate");
        order.setArea(new BigDecimal("100"));
        order.setMaterialCostSqFt(new BigDecimal("1.75"));
        order.setLaborCostSqFt(new BigDecimal("2.10"));
        order.setMaterialCost(order.getMaterialCostSqFt()
                .multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getLaborCostSqFt().multiply(order.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        order.setTax(order.getTaxRate().divide(new BigDecimal("30.00"))
                .multiply((order.getMaterialCost().add(order.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost())
                .add(order.getTax()));

        order = ordersDao.addOrder(order);

        List<Order> fromDao = ordersDao.getOrders(order.getDate());

        //Check line added to the txt file
        assertEquals(1, (fromDao.size() - initialOrders.size()));
    }
    
    @Test
    public void testEditOrder() throws Exception
    {
        LocalDate date = LocalDate.parse("20211014",
            DateTimeFormatter.ofPattern("yyyyMMdd"));

        Order order = new Order();
        order.setDate(date);
        order.setCustomerName("Yyy Name");
        order.setState("NJ");
        order.setTaxRate(new BigDecimal("3.25"));
        order.setProductType("Carpet");
        order.setArea(new BigDecimal("50"));
        order.setMaterialCostSqFt(new BigDecimal("1.75"));
        order.setLaborCostSqFt(new BigDecimal("2.10"));
        order.setMaterialCost(order.getMaterialCostSqFt()
                .multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getLaborCostSqFt().multiply(order.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        order.setTax(order.getTaxRate().divide(new BigDecimal("30.00"), 2, RoundingMode.HALF_UP)
                .multiply((order.getMaterialCost().add(order.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost())
                .add(order.getTax()));

        order = ordersDao.addOrder(order);

        Order editedOrder = order;
        editedOrder.setCustomerName("ABC Name");

        editedOrder = ordersDao.editOrder(editedOrder);

        List<Order> orders = ordersDao.getOrders(date);
        int orderNumber = editedOrder.getOrderNumber();

        Order chosenOrder = orders.stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findFirst().orElse(null);

        assertEquals(editedOrder, chosenOrder);
    }
    
    @Test
    public void testEditOrderFail() throws Exception
    {
        LocalDate date = LocalDate.parse("20211020",
           DateTimeFormatter.ofPattern("yyyyMMdd"));

        Order order = new Order();
        order.setDate(date);
        order.setCustomerName("The Name");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("6.00"));
        order.setProductType("Foam");
        order.setArea(new BigDecimal("10"));
        order.setMaterialCostSqFt(new BigDecimal("1.75"));
        order.setLaborCostSqFt(new BigDecimal("2.10"));
        order.setMaterialCost(order.getMaterialCostSqFt()
                .multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getLaborCostSqFt().multiply(order.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        order.setTax(order.getTaxRate().divide(new BigDecimal("80.00"))
                .multiply((order.getMaterialCost().add(order.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost())
                .add(order.getTax()));

        order = ordersDao.addOrder(order);

        Order editedOrder = order;
        //Order number not listed
        editedOrder.setOrderNumber(0);
        editedOrder.setCustomerName("My Name");

        editedOrder = ordersDao.editOrder(editedOrder);

        //Test.
        assertNull(editedOrder);
    }
    
    @Test
    public void testRemoveOrder() throws Exception
    {
        LocalDate date = LocalDate.parse("20211020",
            DateTimeFormatter.ofPattern("yyyyMMdd"));

        Order orderA = new Order();
        orderA.setDate(date);
        orderA.setCustomerName("Test Company");
        orderA.setState("FL");
        orderA.setTaxRate(new BigDecimal("6.00"));
        orderA.setProductType("Tile");
        orderA.setArea(new BigDecimal("100"));
        orderA.setMaterialCostSqFt(new BigDecimal("1.75"));
        orderA.setLaborCostSqFt(new BigDecimal("2.10"));
        orderA.setMaterialCost(orderA.getMaterialCostSqFt()
                .multiply(orderA.getArea()).setScale(2, RoundingMode.HALF_UP));
        orderA.setLaborCost(orderA.getLaborCostSqFt().multiply(orderA.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        orderA.setTax(orderA.getTaxRate().divide(new BigDecimal("100.00"))
                .multiply((orderA.getMaterialCost().add(orderA.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));
        orderA.setTotal(orderA.getMaterialCost().add(orderA.getLaborCost())
                .add(orderA.getTax()));

        ordersDao.addOrder(orderA);

        Order orderB = new Order();
        orderB.setDate(date);
        orderB.setCustomerName("Other Place");
        orderB.setState("NJ");
        orderB.setTaxRate(new BigDecimal("6.00"));
        orderB.setProductType("Laminate");
        orderB.setArea(new BigDecimal("100"));
        orderB.setMaterialCostSqFt(new BigDecimal("1.75"));
        orderB.setLaborCostSqFt(new BigDecimal("2.10"));
        orderB.setMaterialCost(orderB.getMaterialCostSqFt()
                .multiply(orderB.getArea()).setScale(2, RoundingMode.HALF_UP));
        orderB.setLaborCost(orderB.getLaborCostSqFt().multiply(orderB.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        orderB.setTax(orderB.getTaxRate().divide(new BigDecimal("100.00"))
                .multiply((orderB.getMaterialCost().add(orderB.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));
        orderB.setTotal(orderB.getMaterialCost().add(orderB.getLaborCost())
                .add(orderB.getTax()));

        orderB = ordersDao.addOrder(orderB);

        List<Order> initialOrders = ordersDao.getOrders(date);

        ordersDao.removeOrder(orderB);

        List<Order> fromDao = ordersDao.getOrders(date);

        //Test.
        assertEquals(-1, (fromDao.size() - initialOrders.size()));
    }
    
}
