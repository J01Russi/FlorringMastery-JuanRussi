/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.service;

public class OrderValidationException extends Exception
{

    public OrderValidationException(String message)
    {
        super(message);
    }

    public OrderValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
