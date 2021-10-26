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
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 
 * @author JuanRussi
 */
public class Order
{
    private LocalDate date;
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal materialCostSqFt;
    private BigDecimal laborCostSqFt;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.date);
        hash = 89 * hash + this.orderNumber;
        hash = 89 * hash + Objects.hashCode(this.customerName);
        hash = 89 * hash + Objects.hashCode(this.state);
        hash = 89 * hash + Objects.hashCode(this.taxRate);
        hash = 89 * hash + Objects.hashCode(this.productType);
        hash = 89 * hash + Objects.hashCode(this.area);
        hash = 89 * hash + Objects.hashCode(this.materialCostSqFt);
        hash = 89 * hash + Objects.hashCode(this.laborCostSqFt);
        hash = 89 * hash + Objects.hashCode(this.materialCost);
        hash = 89 * hash + Objects.hashCode(this.laborCost);
        hash = 89 * hash + Objects.hashCode(this.tax);
        hash = 89 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber)
        {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName))
        {
            return false;
        }
        if (!Objects.equals(this.state, other.state))
        {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType))
        {
            return false;
        }
        if (!Objects.equals(this.date, other.date))
        {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate))
        {
            return false;
        }
        if (!Objects.equals(this.area, other.area))
        {
            return false;
        }
        if (!Objects.equals(this.materialCostSqFt, other.materialCostSqFt))
        {
            return false;
        }
        if (!Objects.equals(this.laborCostSqFt, other.laborCostSqFt))
        {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost))
        {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost))
        {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax))
        {
            return false;
        }
        if (!Objects.equals(this.total, other.total))
        {
            return false;
        }
        return true;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public int getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public BigDecimal getTaxRate()
    {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate)
    {
        this.taxRate = taxRate;
    }

    public String getProductType()
    {
        return productType;
    }

    public void setProductType(String productType)
    {
        this.productType = productType;
    }

    public BigDecimal getArea()
    {
        return area;
    }

    public void setArea(BigDecimal area)
    {
        this.area = area;
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

    public BigDecimal getMaterialCost()
    {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost)
    {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost()
    {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost)
    {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax()
    {
        return tax;
    }

    public void setTax(BigDecimal tax)
    {
        this.tax = tax;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

        
    
}
