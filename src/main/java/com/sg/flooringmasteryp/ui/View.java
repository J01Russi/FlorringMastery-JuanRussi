/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.ui;

import com.sg.flooringmasteryp.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author JuanRussi
 */
@Component
public class View
{

    private UserIO io;
    
    @Autowired
    public View(UserIO io)
    {
        this.io = io;
    }

    public void displayMenuHeader()
    {
        io.print("===========================");
        io.print("=== My Flooring Mastery ===");
    }

    public int menuSelection()
    {
        displayMenuHeader();
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Exit");

        return io.readInt("Please select from the"
                + " above options.", 1, 5);
    }

    public void displayContinue()
    {
        io.readString("Please hit enter to continue.");
    }

    public void displayExitBanner()
    {
        io.print("Ok... Bye Bye!!!");
    }

    public void displayRemoveOrderBanner()
    {
        io.print("====== Remove Order =====");
    }

    public void displayErrorMessage(String errorMsg)
    {
        io.print("==== ERROR ====");
        io.print(errorMsg);
        displayContinue();
    }

    public void displayUnknownCommandBanner()
    {
        io.print("Unknown Command...!!!");
        displayContinue();
    }

    public void displayShowOrdersBanner()
    {
        io.print("====== Display Orders =====");
    }

    public void displayAddOrderSuccess(boolean success, Order o)
    {
        if (success == true)
        {
            io.print("Order # " + o.getOrderNumber() + " was successfully added!");
        }
        else
        {
            io.print("Order was not saved.");
            displayContinue();
        }
    }

    public LocalDate inputDate()
    {
        return io.readDate("Please enter a date. (YYYY-mm-dd)",
                LocalDate.of(2021, 10, 1), LocalDate.of(2025, 12, 31));
    }

    public BigDecimal inputArea()
    {
        return io.readBigDecimal("Please enter the area of your project "
                + " in sq feet. ", 2, BigDecimal.ZERO);
    }

    public String inputCustomerName()
    {
        return io.readString("Please enter company's name.");
    }

    public String inputState()
    {
        return io.readString("Please enter state's abbreviation.", 2);
    }

    public String inputProductType()
    {
        return io.readString("Please enter the product for your project.");
    }

    public void displayDateBanner(LocalDate dateChoice)
    {
        System.out.printf("==== Orders on %s ====\n", dateChoice);
    }

    public void displayDateOrders(List<Order> ordersByDate)
    {
        for (Order o : ordersByDate)
        {
            io.print(o.getOrderNumber() + " | " + o.getCustomerName() + " | "
                    + io.formatCurrency(o.getTotal()));
        }
    }

    public Order getOrder()
    {
        Order o = new Order();
        o.setDate(inputDate());
        o.setCustomerName(inputCustomerName());
        o.setState(inputState());
        o.setProductType(inputProductType());
        o.setArea(inputArea());
        return o;
    }

    public void displayOrder(Order o)
    {
        io.print("Date:             \t" + o.getDate());

        io.print("Customer:         \t" + o.getCustomerName());

        io.print("State:            \t" + o.getState());

        io.print("Tax Rate:         \t" + o.getTaxRate());

        io.print("Product:          \t" + o.getProductType());

        io.print("Material Cost per sq ft:\t"
                + io.formatCurrency(o.getMaterialCostSqFt()));

        io.print("Labor Cost per sq ft:\t"
                + io.formatCurrency(o.getLaborCostSqFt()));

        io.print("Area:             \t"
                + o.getArea());

        io.print("Material Cost:    \t"
                + io.formatCurrency(o.getMaterialCost()));

        io.print("Labor Cost:       \t"
                + io.formatCurrency(o.getLaborCost()));

        io.print("Tax:              \t"
                + io.formatCurrency(o.getTax()));

        io.print("Total:            \t"
                + io.formatCurrency(o.getTotal()));
    }

    public String askSave()
    {
        return io.readString("Do you want to save the order? Y/N", 1);
    }

    public void displayEditOrderBanner()
    {
        io.print("==== Edit Order =====");
    }

    public int inputOrderNumber()
    {
        return io.readInt("Please enter an order number.");
    }

    public Order editOrderInfo(Order o)
    {
  
        io.print("Customer:     \t" + o.getCustomerName());
        o.setCustomerName(inputCustomerName());

        io.print("State:        \t" + o.getState());
        o.setState(inputState());

        io.print("Product:      \t" + o.getProductType());
        o.setProductType(inputProductType());

        io.print("Area:         \t" + o.getArea());
        o.setArea(inputArea());

        return o;

    }

    public void displayEditOrderSuccess(boolean success, Order o)
    {
        if (success == true)
        {
            io.print("Order # " + o.getOrderNumber() + " was successfully edited!");
        }
        else
        {
            io.print("Order was not changed.");
            displayContinue();
        }
    }

    public String askRemove()
    {
        return io.readString("Do you want to save the order? Y/N", 1);
    }

    public void displayRemoveOrderSuccess(boolean success, Order o)
    {
        if (success == true)
        {
            io.print("Order # " + o.getOrderNumber() + " was successfully removed!");
        }
        else
        {
            io.print("Order was not removed.");
            displayContinue();
        }
    }

}
