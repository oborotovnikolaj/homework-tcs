package ru.tfs.spring.core.workers;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.tfs.spring.core.framework.Timed;

@Component
@Primary
public class WorkdaySimulator {

    @Timed(isEnable = false)
    public String eat(String ... dishes) throws InterruptedException {
        Thread.sleep(1);
        return "i ate " + String.join(" and ", dishes);
    }

    @Timed(isEnable = true)
    public void watchMemes(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("lol kek");
    }

    @Timed
    public String shit() throws InterruptedException {
        Thread.sleep(500L);
        return "I pooped";
    }
}
