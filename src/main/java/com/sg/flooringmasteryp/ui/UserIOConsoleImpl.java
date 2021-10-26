/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.ui;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_UP;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Scanner;
import org.springframework.stereotype.Component;


/**
 * 
 * @author Juan Russi
 */
public class UserIOConsoleImpl implements UserIO
{

    private Scanner input = new Scanner(System.in);

    @Override
    public void print(String prompt)
    {
        System.out.println(prompt);
    }

    @Override
    public String formatCurrency(BigDecimal amount)
    {
        return NumberFormat.getCurrencyInstance().format(amount);
    }

    @Override
    public String readString(String prompt)
    {
        System.out.println(prompt);
        return input.nextLine();
    }

    @Override
    public String readString(String prompt, int max)
    {
        boolean valid = false;
        String answ = "";
        do
        {
            answ = readString(prompt);
            if (answ.length() <= max)
            {
                valid = true;
            }
            else
            {
                System.out.printf("The entry must be %s letters or less.\n", max);
            }
        }
        while (!valid);
        return answ;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, int scale)
    {
        boolean valid = false;
        BigDecimal answ = null;
        do
        {
            String value = null;
            try
            {
                value = readString(prompt);
                answ = new BigDecimal(value).setScale(scale, ROUND_UP);
                valid = true;
            }
            catch (NumberFormatException ex)
            {
                System.out.printf("The vlue is '%s' is not a number. \n", value);
            }
        }
        while (!valid);
        return answ;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, int scale, BigDecimal min)
    {
        boolean valid = false;
        BigDecimal answ = null;
        do
        {
            answ = readBigDecimal(prompt, scale);
            if (answ.compareTo(min) >= 0)
            {
                valid = true;
            }
            else
            {
                String minString = String.valueOf(min);
                System.out.printf("The value must be grater than %s. \n", minString);
            }
        }
        while (!valid);
        return answ;

    }

    @Override
    public int readInt(String prompt)
    {
        boolean valid = false;
        int answ = 0;
        do
        {
            String value = null;
            try
            {
                value = readString(prompt);
                answ = Integer.parseInt(value);
                valid = true;
            }
            catch (NumberFormatException ex)
            {
                System.out.printf("The value '%s' is not a whole number. \n", value);
            }
        }
        while (!valid);
        return answ;
    }

    @Override
    public int readInt(String prompt, int min, int max)
    {
        boolean valid = false;
        int answ = 0;

        do
        {
            answ = readInt(prompt);
            if (answ >= min && answ <= max)
            {
                valid = true;
            }
            else
            {
                System.out.printf("The value must be between %d and %d.\n", min, max);
            }
        }
        while (!valid);
        return answ;
    }

    @Override
    public LocalDate readDate(String prompt)
    {
        boolean valid = false;
        LocalDate answ = null;
        do
        {
            String value = null;
            try
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                value = readString(prompt);
                answ = LocalDate.parse(value, formatter);
                valid = true;
            }
            catch (DateTimeParseException ex)
            {
                System.out.printf("The value '%s' is not a valid date. (YYYY-MM-DD)\n", value);
            }
        }
        while (!valid);
        return answ;
    }

    @Override
    public LocalDate readDate(String prompt, LocalDate min, LocalDate max)
    {
        boolean valid = false;
        LocalDate answ = null;
        do
        {
            answ = readDate(prompt);
            if (answ.isAfter(min) && answ.isBefore(max))
            {
                valid = true;
            }
            else
            {
                System.out.printf("Please choose a date between. (%s to %s)\n", min, max);
            }
        }
        while (!valid);
        return answ;
    }

}
