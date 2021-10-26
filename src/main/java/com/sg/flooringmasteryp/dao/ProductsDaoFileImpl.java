/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.dao;

import com.sg.flooringmasteryp.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author JuanRussi
 */
public class ProductsDaoFileImpl implements ProductsDao
{

    private static final String PRODUCTS_FILE = "Products.txt";
    private static final String DELIMITER = ",";

    @Override
    public Product getProduct(String productType) throws
            DataPersistenceException
    {
        List<Product> products = loadProducts();
        if (products == null)
        {
            return null;
        }
        else
        {
            Product chosenProduct = products.stream()
                    .filter(p -> p.getProductType().equalsIgnoreCase(productType))
                    .findFirst().orElse(null);
            return chosenProduct;
        }

    }

    private List<Product> loadProducts() throws
            DataPersistenceException
    {
        Scanner sc;
        List<Product> products = new ArrayList<>();

        try
        {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCTS_FILE)));
        }
        catch (FileNotFoundException e)
        {
            throw new DataPersistenceException(
                    "Data not recorded.", e);
        }

        String currentLine;
        String[] currentTokens;
        sc.nextLine();
        while (sc.hasNextLine())
        {
            currentLine = sc.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (currentTokens.length == 3)
            {
                Product currentProduct = new Product();
                currentProduct.setProductType(currentTokens[0]);
                currentProduct.setMaterialCostSqFt(new BigDecimal(currentTokens[1]));
                currentProduct.setLaborCostSqFt(new BigDecimal(currentTokens[2]));
                products.add(currentProduct);
            }
            else
            {

            }
        }
        sc.close();
        if (!products.isEmpty())
        {
            return products;
        }
        else
        {
            return null;
        }
    }

}
