/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.controller;

import com.sg.flooringmasteryp.dao.DataPersistenceException;
import com.sg.flooringmasteryp.dto.Order;
import com.sg.flooringmasteryp.service.FMPServiceL;
import com.sg.flooringmasteryp.service.InvalidOrderNumberException;
import com.sg.flooringmasteryp.service.OrderValidationException;
import com.sg.flooringmasteryp.service.ProductValidationException;
import com.sg.flooringmasteryp.service.StateValidationException;
import com.sg.flooringmasteryp.ui.View;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Administrator
 */
public class Controller
{

    FMPServiceL service;
    View view;

    public Controller(FMPServiceL service, View view)
    {
        this.service = service;
        this.view = view;
        
        
    }

    public void run()
    {
        boolean keepGoing = true;
        int menuSelection = 0;
        try
        {
            while (keepGoing)
            {
                menuSelection = getMenuSelection();

                switch (menuSelection)
                {
                    case 1:
                        getOrdersByDate();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        }
        catch (DataPersistenceException e)
        {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection()
    {
        return view.menuSelection();
    }

    private void getOrdersByDate() 
            throws DataPersistenceException
    {
        LocalDate dateChoice = view.inputDate();
        view.displayDateBanner(dateChoice);
        try
        {
            view.displayDateOrders(service.getOrders(dateChoice));
            view.displayContinue();
        }
        catch (InvalidOrderNumberException e)
        {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() throws DataPersistenceException
    {
        try
        {
            Order o = service.calculateOrder(view.getOrder());
            view.displayOrder(o);
            String answ = view.askSave();
            if (answ.equalsIgnoreCase("Y"))
            {
                service.addOrder(o);
                view.displayAddOrderSuccess(true, o);
            }
            else if (answ.equalsIgnoreCase("N"))
            {
                view.displayAddOrderSuccess(false, o);
            }
            else
            {
                unknownCommand();
            }

        }
        catch (OrderValidationException | StateValidationException
                | ProductValidationException e)
        {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void editOrder() throws DataPersistenceException
    {
        view.displayEditOrderBanner();
        try
        {
            LocalDate dataChoice = view.inputDate();
            int orderNumber = view.inputOrderNumber();
            Order savedOrder = service.getOrder(dataChoice, orderNumber);
            Order orderChanged = view.editOrderInfo(savedOrder);
            Order updatedOrder = service.compareOrders(savedOrder, orderChanged);
            view.displayEditOrderBanner();
            view.displayOrder(orderChanged);
            String answ = view.askSave();
            if (answ.equalsIgnoreCase("Y"))
            {
                service.editOrder(updatedOrder);
                view.displayEditOrderSuccess(true, updatedOrder);
            }
            else if (answ.equalsIgnoreCase("N"))
            {
                view.displayEditOrderSuccess(false, updatedOrder);
            }
            else
            {
                unknownCommand();
            }
        }
        catch (InvalidOrderNumberException | ProductValidationException
                | StateValidationException e)
        {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() throws DataPersistenceException
    {
        view.displayRemoveOrderBanner();
        LocalDate dateChoice = view.inputDate();
        view.displayDateBanner(dateChoice);
        try
        {
            view.displayDateOrders(service.getOrders(dateChoice));
            int orderNumber = view.inputOrderNumber();
            Order o = service.getOrder(dateChoice, orderNumber);
            view.displayRemoveOrderBanner();
            view.displayOrder(o);
            String answ = view.askRemove();
            if (answ.equalsIgnoreCase("Y"))
            {
                service.removeOrder(o);
                view.displayRemoveOrderSuccess(true, o);
            }
            else if (answ.equalsIgnoreCase("N"))
            {
                view.displayRemoveOrderSuccess(false, o);
            }
            else
            {
                unknownCommand();
            }
        }
        catch (InvalidOrderNumberException e)
        {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void unknownCommand()
    {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage()
    {
        view.displayExitBanner();
    }

}
