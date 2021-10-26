/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.service;

public class ProductValidationException extends Exception
{

    public ProductValidationException(String message)
    {
        super(message);
    }

    public ProductValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
