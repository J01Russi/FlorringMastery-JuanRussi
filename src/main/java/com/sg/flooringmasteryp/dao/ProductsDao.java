/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryp.dao;

import com.sg.flooringmasteryp.dto.Product;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface ProductsDao
{
    Product getProduct(String productType) throws 
            DataPersistenceException;

}
