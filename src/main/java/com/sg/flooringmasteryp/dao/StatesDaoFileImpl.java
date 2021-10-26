/**
 *
 * @author JuanRussi
 * email: juan@smrtdata.net
 * date:
 * where in the world: Vancouver B.C.
 * purpose:
 */
package com.sg.flooringmasteryp.dao;

import com.sg.flooringmasteryp.dto.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author JuanRussi
 */
public class StatesDaoFileImpl implements StatesDao
{

    private static final String STATES_FILE = "Taxes.txt";
    private static final String DELIMITER = ",";

    @Override
    public State getState(String state) throws
            DataPersistenceException
    {
        List<State> states = loadStates();
        if (states == null)
        {
            return null;
        }
        else
        {
            State chosenState = states.stream()
                    .filter(s -> s.getState().equalsIgnoreCase(state))
                    .findFirst().orElse(null);
            return chosenState;
        }
    }

    private List<State> loadStates() throws
            DataPersistenceException
    {
        Scanner sc;
        List<State> states = new ArrayList<>();

        try
        {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(STATES_FILE)));
        }
        catch (FileNotFoundException e)
        {
            throw new DataPersistenceException("Culd not save state data.");
        }
        String currentLine;
        String[] currentTokens;
        sc.nextLine();
        while (sc.hasNextLine())
        {
            currentLine = sc.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (currentTokens.length == 2)
            {
                State currentState = new State();
                currentState.setState(currentTokens[0]);
                currentState.setTaxRate(new BigDecimal(currentTokens[1]));
                states.add(currentState);
            }
            else
            {
            }
        }
        sc.close();

        if (!states.isEmpty())
        {
            return states;
        }
        else
        {
            return null;
        }
    }

}
