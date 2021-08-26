package ru.tfs.spring.core.framework;

import java.time.LocalDateTime;

/**
 * Класс метрики вызова метода
 */

public class MethodInvocationMetric {
    /**
     * Наименование метода, он же уникальный идентификатор
     * <p>
     * Прим: org.demo.repository.OrderRepository.getById
     */
    private String method;
    /**
     * Время вызова метода
     */
    private LocalDateTime invocationTime;
    /**
     * Время работы метода
     */
    private Integer totalTime;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getInvocationTime() {
        return invocationTime;
    }

    public void setInvocationTime(LocalDateTime invocationTime) {
        this.invocationTime = invocationTime;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }
}