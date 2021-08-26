package ru.tfs.spring.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.tfs.spring.core.framework.MethodMetricStat;
import ru.tfs.spring.core.framework.MetricStatProvider;
import ru.tfs.spring.core.workers.SoftwareWorkdaySimulator;
import ru.tfs.spring.core.workers.WorkdaySimulator;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@TestPropertySource("classpath:application.properties")
class MetricStatProviderTest {

    @Autowired
    WorkdaySimulator person1;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MetricStatProvider provider;

    //Аннотация на методах
    @Test
    public void workDayTest() throws InterruptedException {

        provider.clean();

        ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        WorkdaySimulator person2 = applicationContext.getBean(WorkdaySimulator.class);

        for(int i = 0; i < 4; i++) {
            executorService.submit(() -> person1.eat("krabsburger", "Parmesan"));
            executorService.submit(() -> person2.eat("Foie gras", "Jamon"));
            executorService.submit(() -> person1.watchMemes(1));
            executorService.submit(() -> person2.watchMemes(1));
            executorService.submit(person1::shit);
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(2, TimeUnit.SECONDS));

        //Проверка максимального и минимального времени и выборки про времени
        LocalDateTime timeFrom = LocalDateTime.now();
        Thread.sleep(500);
        person1.watchMemes(5);
        person1.watchMemes(0);
        LocalDateTime timeTo = LocalDateTime.now();
        Thread.sleep(500);
        person1.watchMemes(6);

        //Один из трех методов был с @Timed(isEnable = false)
        Assertions.assertEquals(2, provider.getTotalStat().size());

        MethodMetricStat shitStat = provider.getTotalStatByMethod("ru.tfs.spring.core.workers.WorkdaySimulator#shit");
        MethodMetricStat watchMemesStat = provider.getTotalStatByMethod("ru.tfs.spring.core.workers.WorkdaySimulator#watchMemes");


        Assertions.assertTrue(shitStat.getAverageTime() > 500);
        Assertions.assertTrue(watchMemesStat.getAverageTime() > 1000);

        Assertions.assertTrue(watchMemesStat.getMinTime() < 250);
        Assertions.assertTrue(watchMemesStat.getMaxTime() >= 6000);

        Assertions.assertEquals(shitStat.getInvocationsCount(), 4);
        //часть запусков удалили
        Assertions.assertEquals(watchMemesStat.getInvocationsCount(), 10);

        MethodMetricStat watchMemesStatForPeriod = provider.getTotalStatByMethodForPeriod(
                "ru.tfs.spring.core.workers.WorkdaySimulator#watchMemes", timeFrom, timeTo);

        Assertions.assertTrue(watchMemesStatForPeriod.getMaxTime() >= 5000);
        Assertions.assertTrue(watchMemesStatForPeriod.getMaxTime() < 6000);
    }

    //Аннотация на классе
    @Test
    public void itEngineerTest() throws InterruptedException {

        provider.clean();

        ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        SoftwareWorkdaySimulator itEngineer = applicationContext.getBean(SoftwareWorkdaySimulator.class);

        for(int i = 0; i < 500; i++) {
            executorService.submit(() -> itEngineer.drinkTea("Black"));
            executorService.submit(itEngineer::writeSomeCode);
            executorService.submit(() -> itEngineer.drinkTea("Green"));
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(2, TimeUnit.SECONDS));

        Assertions.assertEquals(provider.getTotalStat().size(), 2);
    }
}