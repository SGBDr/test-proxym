package com.proxym.libraryapp.library.laterreturnstrategy;

import com.proxym.libraryapp.library.laterreturnstrategy.strategies.LaterResidentReturnStrategy;
import com.proxym.libraryapp.library.laterreturnstrategy.strategies.LaterStudentReturnStrategy;
import com.proxym.libraryapp.member.Resident;
import com.proxym.libraryapp.member.Student;

import java.util.HashMap;

public class LaterReturnStrategyCreator {
    public static LaterReturnStrategy getStrategy(Class cl) {
        HashMap<Class, LaterReturnStrategy> strategies = new HashMap<>();
        strategies.put(Student.class, new LaterStudentReturnStrategy());
        strategies.put(Resident.class, new LaterResidentReturnStrategy());

        LaterReturnStrategy strategy = strategies.get(cl);

        if(strategy == null)
            throw new NotFoundLaterReturnStrategyException(cl.toString());

        return strategy;
    }
}
