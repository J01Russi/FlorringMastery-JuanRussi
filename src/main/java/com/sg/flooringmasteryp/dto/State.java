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

public class State
{
    private String state;
    private BigDecimal taxRate;

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
    
    

}
