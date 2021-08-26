package ru.tfs.spring.core.workers;

import org.springframework.stereotype.Component;
import ru.tfs.spring.core.framework.Timed;

@Component
@Timed
public class SoftwareWorkdaySimulator extends WorkdaySimulator{

    public String writeSomeCode() throws InterruptedException {
        Thread.sleep(500L);
        return "I am tired";
    }


    public String drinkTea(String color) throws InterruptedException {
        Thread.sleep(250L);
        return "One more cup of " + color + " tea";
    }
}
