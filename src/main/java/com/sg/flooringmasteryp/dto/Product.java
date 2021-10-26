/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.dto;

import java.math.BigDecimal;

public class Product
{
    private String productType;
    private BigDecimal materialCostSqFt;
    private BigDecimal laborCostSqFt;

    public String getProductType()
    {
        return productType;
    }

    public void setProductType(String productType)
    {
        this.productType = productType;
    }

    public BigDecimal getMaterialCostSqFt()
    {
        return materialCostSqFt;
    }

    public void setMaterialCostSqFt(BigDecimal materialCostSqFt)
    {
        this.materialCostSqFt = materialCostSqFt;
    }

    public BigDecimal getLaborCostSqFt()
    {
        return laborCostSqFt;
    }

    public void setLaborCostSqFt(BigDecimal laborCostSqFt)
    {
        this.laborCostSqFt = laborCostSqFt;
    }
    
    

}
