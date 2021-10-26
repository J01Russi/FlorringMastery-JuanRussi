/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryp.dao;

import com.sg.flooringmasteryp.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author JuanRussi
 */
public interface OrdersDao
{

    List<Order> getOrders(LocalDate datechoice) throws DataPersistenceException;

    Order addOrder(Order o) throws DataPersistenceException;

    Order editOrder(Order orderChanged) throws DataPersistenceException;

    Order removeOrder(Order o) throws DataPersistenceException;

}
