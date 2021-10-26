/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.dao;

/**
 * 
 * @author JuanRussi
 */
public class DataPersistenceException extends Exception
{

    public DataPersistenceException(String message)
    {
        super(message);
    }

    public DataPersistenceException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
