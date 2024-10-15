package com.proxym.libraryapp.library.paybookstrategy;

import com.proxym.libraryapp.library.paybookstrategy.strategies.ResidentPayStrategy;
import com.proxym.libraryapp.library.paybookstrategy.strategies.StudentPayStrategy;
import com.proxym.libraryapp.member.Resident;
import com.proxym.libraryapp.member.Student;

import java.util.HashMap;

public class PayBookStrategyCreator {
    public static PayStrategy getStrategy(Class cl) {
        HashMap<Class, PayStrategy> strategies = new HashMap<>();
        strategies.put(Student.class, new StudentPayStrategy());
        strategies.put(Resident.class, new ResidentPayStrategy());

        PayStrategy strategy = strategies.get(cl);

        if(strategy == null)
            throw new NotFoundPayBookStrategyException(cl.toString());

        return strategy;
    }
}
