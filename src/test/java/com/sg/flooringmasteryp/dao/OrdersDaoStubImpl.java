/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.dao;

import com.sg.flooringmasteryp.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JuanRussi
 */
public class OrdersDaoStubImpl implements OrdersDao
{

    private Order onlyOrder;
    private List<Order> ordersList = new ArrayList<>();

    public OrdersDaoStubImpl()
    {
        onlyOrder = new Order();
        onlyOrder.setDate(LocalDate.parse("20211020",
                DateTimeFormatter.ofPattern("yyyyMMdd")));
        onlyOrder.setOrderNumber(1);
        onlyOrder.setCustomerName("Xxx Name");
        onlyOrder.setState("NY");
        onlyOrder.setTaxRate(new BigDecimal("3.00"));
        onlyOrder.setProductType("Foam");
        onlyOrder.setArea(new BigDecimal("30"));
        onlyOrder.setMaterialCostSqFt(new BigDecimal("1.25"));
        onlyOrder.setLaborCostSqFt(new BigDecimal("3.20"));
        onlyOrder.setMaterialCost(onlyOrder.getMaterialCostSqFt()
                .multiply(onlyOrder.getArea()).setScale(2, RoundingMode.HALF_UP));
        onlyOrder.setLaborCost(onlyOrder.getLaborCostSqFt().multiply(onlyOrder.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        onlyOrder.setTax(onlyOrder.getTaxRate().divide(new BigDecimal("30.00"))
                .multiply((onlyOrder.getMaterialCost().add(onlyOrder.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));
        onlyOrder.setTotal(onlyOrder.getMaterialCost().add(onlyOrder.getLaborCost())
                .add(onlyOrder.getTax()));

        ordersList.add(onlyOrder);

    }

    @Override
    public List<Order> getOrders(LocalDate chosenDate) throws
            DataPersistenceException
    {
        if (chosenDate.equals(onlyOrder.getDate()))
        {
            return ordersList;
        }
        else
        {

            return new ArrayList<>();
        }
    }

    @Override
    public Order addOrder(Order o) throws DataPersistenceException
    {
        ordersList.add(o);
        return o;
    }

    @Override
    public Order editOrder(Order orderChanged) throws DataPersistenceException
    {
        if (orderChanged.getOrderNumber() == onlyOrder.getOrderNumber())
        {
            return onlyOrder;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Order removeOrder(Order o) throws DataPersistenceException
    {
        if (o.equals(onlyOrder))
        {
            return onlyOrder;
        }
        else
        {
            return null;
        }
    }

}
