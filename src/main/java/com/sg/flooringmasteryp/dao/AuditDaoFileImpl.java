

/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date: 
 * where in the world: Vancouver B.C.
 * purpose: 
 */

package com.sg.flooringmasteryp.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 * 
 * @author JuanRussi
 */
public class AuditDaoFileImpl implements AuditDao
{
    public static final String AUDIT_FILE = "Audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws DataPersistenceException
    {
        PrintWriter out;
        try
        {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        }
        catch (IOException e)
        {
            throw new DataPersistenceException("Couldn't create information. ", e);
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
    

}
