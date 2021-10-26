/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryp.service;

import com.sg.flooringmasteryp.dao.AuditDao;
import com.sg.flooringmasteryp.dao.AuditDaoStubImpl;
import com.sg.flooringmasteryp.dao.OrdersDao;
import com.sg.flooringmasteryp.dao.OrdersDaoStubImpl;
import com.sg.flooringmasteryp.dao.ProductsDao;
import com.sg.flooringmasteryp.dao.ProductsDaoFileImpl;
import com.sg.flooringmasteryp.dao.StatesDao;
import com.sg.flooringmasteryp.dao.StatesDaoFileImpl;
import com.sg.flooringmasteryp.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;



/**
 *
 * @author JuanRussi
 */

public class FMPServiceLImplTest
{

    private FMPServiceL service;
    
    
    public FMPServiceLImplTest()
    {
        OrdersDao ordersDao = new OrdersDaoStubImpl();
        ProductsDao productsDao = new ProductsDaoFileImpl();
        StatesDao statesDao = new StatesDaoFileImpl();
        AuditDao auditDao = new AuditDaoStubImpl();

        service = new FMPServiceLImpl(ordersDao, productsDao, statesDao, auditDao);
        
//        ApplicationContext ctx = 
//            new ClassPathXmlApplicationContext("applicationContext.xml");
//        service = ctx.getBean("serviceLTest", FMPServiceLImpl.class);
//        
    }
    
//    ApplicationContext ctx = 
//        new ClassPathXmlApplicationContext("applicationContext.xml");
//    service = ctx.getBean("FMPServiceL", FMPServiceLImplTest.class);

    @Test
    public void testGetOrders() throws Exception
    {
        assertEquals(1, service.getOrders(LocalDate.of(2021, 10, 14)).size());

        try
        {
            List<Order> orders = service.getOrders(LocalDate.of(2021, 10, 14));
            fail("Expected Failed Test.");
        }
        catch (InvalidOrderNumberException e)
        {
        }
    }

    @Test
    public void testGetOrder() throws Exception
    {
        Order order = service.getOrder(LocalDate.of(2021, 10, 14), 10);
        assertNotNull(order);

        try
        {
            order = service.getOrder(LocalDate.of(2021, 10, 14), 10);
            fail("Expected order number failed.");
        }
        catch (InvalidOrderNumberException e)
        {
        }
        try
        {
            service.getOrder(LocalDate.of(2021, 10, 14), 10);
            fail("Expected order number failed.");
        }
        catch (InvalidOrderNumberException e)
        {
        }
    }

    @Test
    public void testCalculateOrder() throws Exception
    {
        Order orderOne = new Order();
        orderOne.setCustomerName("My Test Name 1");
        orderOne.setState("NJ");
        orderOne.setProductType("Carpet");
        orderOne.setArea(new BigDecimal("100"));

        Order orderTwo = new Order();
        orderTwo.setCustomerName("My Test Name 2");
        orderTwo.setState("NY");
        orderTwo.setProductType("Foam");
        orderTwo.setArea(new BigDecimal("100"));

        assertEquals(service.calculateOrder(orderOne), service.calculateOrder(orderTwo));

        orderOne.setCustomerName("");

        try
        {
            service.calculateOrder(orderOne);
            fail("Expected Order check not tested.");
        }
        catch (OrderValidationException e)
        {
        }

        orderOne.setCustomerName("The Name");
        orderOne.setState("");

        try
        {
            service.calculateOrder(orderOne);
            fail("Expected Order check not tested.");
        }
        catch (OrderValidationException e)
        {
        }

        orderOne.setState("NJ");
        orderOne.setProductType("");

        try
        {
            service.calculateOrder(orderOne);
            fail("Expected Order check not tested.");
        }
        catch (OrderValidationException e)
        {
        }

        orderOne.setProductType("Carpet");
        orderOne.setArea(new BigDecimal("0"));

        try
        {
            service.calculateOrder(orderOne);
            fail("Expected Order check not tested.");
        }
        catch (OrderValidationException e)
        {
        }

        orderOne.setArea(new BigDecimal("100"));
        orderOne.setState("");

        try
        {
            service.calculateOrder(orderOne);
            fail("Expected Order check not tested.");
        }
        catch (OrderValidationException e)
        {
        }

        orderOne.setState("NJ");
        orderOne.setProductType("");

        try
        {
            service.calculateOrder(orderOne);
            fail("Expected Order check not tested.");
        }
        catch (OrderValidationException e)
        {
        }
    }
    
    @Test
    public void testAddOrder() throws Exception
    {
        Order order = new Order();
        order.setCustomerName("My Test Name 1");
        order.setState("NJ");
        order.setProductType("Carpet");
        order.setArea(new BigDecimal("50"));
        
        service.addOrder(order);
        
        assertEquals(order,service.addOrder(order));
        
    }
    
    @Test
    public void testEditOrder() throws Exception
    {
        Order savedOrder = service.getOrder(LocalDate.of(2021, 10, 10), 14);
        assertNotNull(savedOrder);
        
        try
        {
            savedOrder = service.getOrder(LocalDate.of(2021, 10, 10), 14);
            fail("Expected Order check not tested.");
        }
        catch (InvalidOrderNumberException e)
        {
        }
    }
    
    @Test
    public void testRemoveOrder() throws Exception
    {
        Order removedOrder = service.getOrder(LocalDate.of(2021, 10, 10), 14);
        assertNotNull(removedOrder);
        
        try
        {
            removedOrder = service.getOrder(LocalDate.of(2021, 10, 10), 14);
            fail("Expected Order check not tested.");
        }
        catch (InvalidOrderNumberException e)
        {
        }
    }

}
