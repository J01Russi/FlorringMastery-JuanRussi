/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryp.service;

import com.sg.flooringmasteryp.dao.DataPersistenceException;
import com.sg.flooringmasteryp.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author JuanRussi
 */
public interface FMPServiceL
{

    List <Order> getOrders(LocalDate dateChoise) throws InvalidOrderNumberException,
            DataPersistenceException;

    Order calculateOrder(Order o) throws DataPersistenceException,
            OrderValidationException, StateValidationException, ProductValidationException;

    Order getOrder(LocalDate dateChoise, int orderNumber) throws
            DataPersistenceException, InvalidOrderNumberException;

    Order addOrder(Order o) throws DataPersistenceException;

    Order compareOrders(Order savedOrder, Order orderChanged)
            throws DataPersistenceException, StateValidationException,
            ProductValidationException;

    Order editOrder(Order updatedOrder) throws DataPersistenceException,
            InvalidOrderNumberException;

    Order removeOrder(Order removedOrder) throws DataPersistenceException,
            InvalidOrderNumberException;

}
