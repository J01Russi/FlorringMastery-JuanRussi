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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author JuanRussi
 */
public class OrdersDaoFileImpl implements OrdersDao
{

    private int lastOrderNumber;
    private static final String HEADER = "OrderNumber,CustomerName,State,TaxRate,"
            + "ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
            + "MaterialCost,LaborCost,Tax,Total";
    private static final String DELIMITER = ",";
    private String dataFolder = "orders/";
    
    
    public OrdersDaoFileImpl()
    {
    }
    
    
    public OrdersDaoFileImpl(String dataFolder)
    {
        this.dataFolder = dataFolder;
    }

    private void readLastOrderNumber() throws DataPersistenceException
    {
        Scanner sc;

        try
        {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(dataFolder + "LastOrderNumber.txt")));
        }
        catch (FileNotFoundException e)
        {
            throw new DataPersistenceException(
                    "Order Nuber did not load to the file.", e);
        }
        int savedOrderNumber = Integer.parseInt(sc.nextLine());

        this.lastOrderNumber = savedOrderNumber;
        sc.close();
    }

    private void writeLastOrderNumber(int lastOrderNumber)
            throws DataPersistenceException
    {
        PrintWriter out;
        try
        {
            out = new PrintWriter(new FileWriter(dataFolder + "LastOrderNumber.txt"));
        }
        catch (IOException e)
        {
            throw new DataPersistenceException("Culd not save order number.", e);
        }

        out.println(lastOrderNumber);

        out.flush();

        out.close();

    }

    private Order cleanFields(Order order)
    {
        String cleanCustomerName = order.getCustomerName().replace(DELIMITER, "");

        String cleanState = order.getState().replace(DELIMITER, "");

        String cleanProductType = order.getProductType().replace(DELIMITER, "");

        order.setCustomerName(cleanCustomerName);
        order.setState(cleanState);
        order.setProductType(cleanProductType);

        return order;
    }

    private List<Order> loadOrders(LocalDate chosenDate) throws DataPersistenceException
    {
        Scanner sc;
        String fileDate = chosenDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        File f = new File(String.format(dataFolder + "Orders_%s.txt", fileDate));

        List<Order> orders = new ArrayList<>();

        if (f.isFile())
        {
            try
            {
                sc = new Scanner(
                        new BufferedReader(
                                new FileReader(f)));
            }
            catch (FileNotFoundException e)
            {
                throw new DataPersistenceException(
                        "Order Nuber did not load to the file.", e);
            }
            String currentLine;
            String[] currentTokens;
            sc.nextLine();
            while (sc.hasNextLine())
            {
                currentLine = sc.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                if (currentTokens.length > 12)
                {
                    Order currentOrder = new Order();
                    currentOrder.setDate(LocalDate.parse(fileDate,
                            DateTimeFormatter.ofPattern("yyyyMMdd")));
                    currentOrder.setOrderNumber(Integer.parseInt(currentTokens[0]));
                    currentOrder.setCustomerName(currentTokens[1]);
                    currentOrder.setState(currentTokens[2]);
                    currentOrder.setTaxRate(new BigDecimal(currentTokens[3]));
                    currentOrder.setProductType(currentTokens[4]);
                    currentOrder.setArea(new BigDecimal(currentTokens[5]));
                    currentOrder.setMaterialCostSqFt(new BigDecimal(currentTokens[6]));
                    currentOrder.setLaborCostSqFt(new BigDecimal(currentTokens[7]));
                    currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
                    currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
                    currentOrder.setTax(new BigDecimal(currentTokens[10]));
                    currentOrder.setTotal(new BigDecimal(currentTokens[11]));
                    orders.add(currentOrder);
                }
                else
                {
                }
            }
            sc.close();
            return orders;
        }
        else
        {
            return orders;
        }
    }

    private void writeOrders(List<Order> orders, LocalDate orderDate) throws
            DataPersistenceException
    {
        PrintWriter out;

        String fileDate = orderDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        File f = new File(String.format(dataFolder + "Orders_%s.txt", fileDate));

        try
        {
            out = new PrintWriter(new FileWriter(f));
        }
        catch (IOException e)
        {
            throw new DataPersistenceException("Order Nuber did not load to the file.", e);
        }

        out.println(HEADER);
        for (Order currentOrder : orders)
        {
            out.println(currentOrder.getOrderNumber() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getMaterialCostSqFt() + DELIMITER
                    + currentOrder.getLaborCostSqFt() + DELIMITER
                    + currentOrder.getMaterialCostSqFt() + DELIMITER
                    + currentOrder.getMaterialCost() + DELIMITER
                    + currentOrder.getLaborCost() + DELIMITER
                    + currentOrder.getTax() + DELIMITER
                    + currentOrder.getTotal());
            out.flush();
        }
        out.close();
    }

    @Override
    public List<Order> getOrders(LocalDate chosenDate) throws
            DataPersistenceException
    {
        return loadOrders(chosenDate);
    }

    @Override
    public Order addOrder(Order o) throws
            DataPersistenceException
    {
        o = cleanFields(o);
        readLastOrderNumber();
        lastOrderNumber++;
        o.setOrderNumber(lastOrderNumber);
        writeLastOrderNumber(lastOrderNumber);

        List<Order> orders = loadOrders(o.getDate());
        orders.add(o);
        writeOrders(orders, o.getDate());

        return o;
    }

    @Override
    public Order editOrder(Order orderChanged) throws
            DataPersistenceException
    {
        orderChanged = cleanFields(orderChanged);
        int orderNumber = orderChanged.getOrderNumber();

        List<Order> orders = loadOrders(orderChanged.getDate());
        Order chosenOrder = orders.stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findFirst().orElse(null);

        if (chosenOrder != null)
        {
            int index = orders.indexOf(chosenOrder);
            orders.set(index, orderChanged);
            writeOrders(orders, orderChanged.getDate());
            return orderChanged;
        }
        else
        {
            return null;
        }

    }

    @Override
    public Order removeOrder(Order chosenOrder) throws
            DataPersistenceException
    {
        int orderNumber = chosenOrder.getOrderNumber();

        List<Order> orders = loadOrders(chosenOrder.getDate());
        Order removedOrder = orders.stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findFirst().orElse(null);

        if (removedOrder != null)
        {
            orders.remove(removedOrder);
            writeOrders(orders, chosenOrder.getDate());
            return removedOrder;
        }
        else
        {
            return null;
        }

    }

}
