/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryp.dao;

import com.sg.flooringmasteryp.dto.State;

/**
 *
 * @author JuanRussi
 */
public interface StatesDao
{
    State getState(String stateAbbr) throws 
            DataPersistenceException;
}
