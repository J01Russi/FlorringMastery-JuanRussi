/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.service;

public class StateValidationException extends Exception
{

    public StateValidationException(String message)
    {
        super(message);
    }

    public StateValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
