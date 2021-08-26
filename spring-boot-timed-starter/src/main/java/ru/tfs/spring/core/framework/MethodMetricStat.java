package ru.tfs.spring.core.framework;

import java.util.List;

/**
 * Статистика метрик метода
 */
public class MethodMetricStat {
    /**
     * Наименование/идентификатор метода
     */
    private String methodName;
    /**
     * Кол-во вызовов метода
     */
    private Integer invocationsCount;
    /**
     * Минимальное время работы метода
     */
    private Integer minTime;
    /**
     * Среднее время работы метода
     */
    private Integer averageTime;
    /**
     * максимальное время работы метода
     */
    private Integer maxTime;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getInvocationsCount() {
        return invocationsCount;
    }

    public void setInvocationsCount(Integer invocationsCount) {
        this.invocationsCount = invocationsCount;
    }

    public Integer getMinTime() {
        return minTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    public Integer getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Integer averageTime) {
        this.averageTime = averageTime;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public MethodMetricStat() {
    }

    public static MethodMetricStat creatMethodMetricStat(List<MethodInvocationMetric> invocations) {

        if (invocations == null || invocations.size() < 1) {
            return null;
        }

        Integer sum = null;
        Integer minTime = null;
        Integer maxTime = null;

        for (MethodInvocationMetric invocation : invocations) {
            if (maxTime == null || invocation.getTotalTime() > maxTime) {
                maxTime = invocation.getTotalTime();
            }
            if (minTime == null || invocation.getTotalTime() < minTime) {
                minTime = invocation.getTotalTime();
            }
            sum = sum == null ? invocation.getTotalTime() : sum + invocation.getTotalTime();
        }
        Integer invocationsCount = invocations.size();
        Integer averageTime = sum == null ? 0 : sum / invocationsCount;

        MethodMetricStat stat = new MethodMetricStat();
        stat.setInvocationsCount(invocationsCount);
        stat.setMethodName(invocations.get(0).getMethod());
        stat.setAverageTime(averageTime);
        stat.setMaxTime(maxTime);
        stat.setMinTime(minTime);

        return stat;
    }
}
