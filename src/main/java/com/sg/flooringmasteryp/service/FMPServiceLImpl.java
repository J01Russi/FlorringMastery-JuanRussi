/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.service;

import com.sg.flooringmasteryp.dao.AuditDao;
import com.sg.flooringmasteryp.dao.DataPersistenceException;
import com.sg.flooringmasteryp.dao.OrdersDao;
import com.sg.flooringmasteryp.dao.ProductsDao;
import com.sg.flooringmasteryp.dao.StatesDao;
import com.sg.flooringmasteryp.dto.Order;
import com.sg.flooringmasteryp.dto.Product;
import com.sg.flooringmasteryp.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author JuanRussi
 */
public class FMPServiceLImpl implements FMPServiceL
{

    private OrdersDao ordersDao;
    private ProductsDao productsDao;
    private StatesDao statesDao;
    private AuditDao auditDao;

    public FMPServiceLImpl(OrdersDao ordersDao, ProductsDao productsDao,
            StatesDao statesDao, AuditDao auditDao)
    {
        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.statesDao = statesDao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Order> getOrders(LocalDate chosenDate) throws
            InvalidOrderNumberException, DataPersistenceException
    {
        //Chosen date format
        List<Order> ordersByDate = ordersDao.getOrders(chosenDate);
        if (!ordersByDate.isEmpty())
        {
            return ordersByDate;
        }
        else
        {
            throw new InvalidOrderNumberException("ERROR: No Orders "
                    + " exist on tha date.");
        }
    }

    @Override
    public Order calculateOrder(Order o) throws DataPersistenceException,
            OrderValidationException, StateValidationException, ProductValidationException
    {
        validateOrder(o);
        calculateTax(o);
        calculateMaterial(o);
        calculateTotal(o);

        return o;

    }

    @Override
    public Order getOrder(LocalDate chosenDate, int orderNumber) throws
            DataPersistenceException, InvalidOrderNumberException
    {
        List<Order> orders = getOrders(chosenDate);
        Order chosenOrder = orders.stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findFirst().orElse(null);
        if (chosenOrder != null)
        {
            return chosenOrder;
        }
        else
        {
            throw new InvalidOrderNumberException("ERROR: No orders with that "
                    + " number exist on that date.");
        }

    }

    @Override
    public Order addOrder(Order o) throws DataPersistenceException
    {
        ordersDao.addOrder(o);
        auditDao.writeAuditEntry("Order #"
                + o.getOrderNumber() + " for date "
                + o.getDate() + " ADDED.");
        return o;
    }

    @Override
    public Order compareOrders(Order savedOrder, Order orderChanged) throws
            DataPersistenceException, StateValidationException, ProductValidationException
    {
        if (orderChanged.getCustomerName() == null
                || orderChanged.getCustomerName().trim().equals(""))
        {
            // Do nothing
        }
        else
        {
            savedOrder.setCustomerName(orderChanged.getCustomerName());
        }

        if (orderChanged.getState() == null
                || orderChanged.getState().trim().equals(""))
        {
            // Do nothing
        }
        else
        {
            savedOrder.setState(orderChanged.getState());
            calculateTax(savedOrder);
        }

        if (orderChanged.getProductType() == null
                || orderChanged.getProductType().trim().equals(""))
        {
            // Do nothing
        }
        else
        {
            savedOrder.setProductType(orderChanged.getProductType());
            calculateMaterial(savedOrder);
        }

        if (orderChanged.getArea() == null
                || (orderChanged.getArea().compareTo(BigDecimal.ZERO)) == 0)
        {
            // Do nothing
        }
        else
        {
            savedOrder.setArea(orderChanged.getArea());
        }
        calculateTotal(savedOrder);
        return savedOrder;

    }

    @Override
    public Order editOrder(Order updatedOrder) throws
            DataPersistenceException, InvalidOrderNumberException
    {
        updatedOrder = ordersDao.editOrder(updatedOrder);
        if (updatedOrder != null)
        {
            auditDao.writeAuditEntry("Order #"
                    + updatedOrder.getOrderNumber() + " for date "
                    + updatedOrder.getDate() + " EDITED. ");
            return updatedOrder;
        }
        else
        {
            throw new InvalidOrderNumberException("ERROR: No orders"
                    + " with that number exist on that date.");
        }

    }

    @Override
    public Order removeOrder(Order removedOrder) throws DataPersistenceException,
            InvalidOrderNumberException
    {
        removedOrder = ordersDao.removeOrder(removedOrder);
        if (removedOrder != null)
        {
            auditDao.writeAuditEntry("Order #"
                    + removedOrder.getOrderNumber() + " for date "
                    + removedOrder.getDate() + " REMOVED.");
            return removedOrder;
        }
        else
        {
            throw new InvalidOrderNumberException("ERROR: No orders with that number "
                    + "exist on that date.");
        }

    }

    private void calculateMaterial(Order o) throws DataPersistenceException,
            ProductValidationException
    {
        Product chosenProduct = productsDao.getProduct(o.getProductType());
        if (chosenProduct == null)
        {
            throw new ProductValidationException("ERROR: We don not sell"
                    + " this product.");
        }
        o.setProductType(chosenProduct.getProductType());
        o.setMaterialCostSqFt(chosenProduct.getMaterialCostSqFt());
        o.setLaborCostSqFt(chosenProduct.getLaborCostSqFt());

    }

    private void calculateTax(Order o) throws DataPersistenceException,
            StateValidationException
    {
        State chosenState = statesDao.getState(o.getState());
        if (chosenState == null)
        {
            throw new StateValidationException("ERROR: we don not work in "
                    + " that state.");
        }
        o.setState(chosenState.getState());
        o.setTaxRate(chosenState.getTaxRate());

    }

    private void calculateTotal(Order o)
    {
        o.setMaterialCost(o.getMaterialCostSqFt().multiply(o.getArea())
                .setScale(2, RoundingMode.HALF_UP));

        o.setLaborCost(o.getLaborCostSqFt().multiply(o.getArea())
                .setScale(2, RoundingMode.HALF_UP));

        o.setTax(o.getTaxRate().divide(new BigDecimal("100.00"))
                .multiply((o.getMaterialCost().add(o.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));

        o.setTotal(o.getMaterialCost().add(o.getLaborCost()).add(o.getTax()));

    }

    private void validateOrder(Order o) throws OrderValidationException
    {
        String message = "";
        if (o.getCustomerName().trim().isEmpty() || o.getCustomerName() == null)
        {
            message += "Customer name is required.\n";
        }

        if (o.getState().trim().isEmpty() || o.getState() == null)
        {
            message += "State is required.\n";
        }

        if (o.getProductType().trim().isEmpty() || o.getProductType() == null)
        {
            message += "Product name is required.\n";
        }

        if (o.getArea().compareTo(BigDecimal.ZERO) == 0 || o.getArea() == null)
        {
            message += "Area Square Footage is required.\n";
        }

        if (!message.isEmpty())
        {
            throw new OrderValidationException(message);
        }

    }

}
