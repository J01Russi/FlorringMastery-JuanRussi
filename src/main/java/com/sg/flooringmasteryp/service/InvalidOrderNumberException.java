/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.service;

public class InvalidOrderNumberException extends Exception
{
    InvalidOrderNumberException(String message)
    {
        super (message);
    }
    
    InvalidOrderNumberException(String message, Throwable cause)
    {
        super (message, cause);
    }

}
