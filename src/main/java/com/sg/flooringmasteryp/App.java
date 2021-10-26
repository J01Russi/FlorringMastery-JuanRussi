/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date: Oct 3, 2021
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp;

import com.sg.flooringmasteryp.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 
 * @author Juan
 */
public class App
{
    
    public static void main(String[] args)
    {
//        UserIO myIO = new UserIOConsoleImpl();
//        View myView = new View(myIO);
//        OrdersDao myDao = new OrdersDaoFileImpl();
//        AuditDao myAuditDao = new AuditDaoFileImpl();
        
        
//        ApplicationContext ctx 
//                = new  ClassPathXmlApplicationContext("applicationContext.xml");
//        
//        Controller controller = ctx.getBean("controller", Controller.class);
//        controller.run();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
            
            Controller controller = ctx.getBean("controller", Controller.class);
            controller.run();
        
    }

}
