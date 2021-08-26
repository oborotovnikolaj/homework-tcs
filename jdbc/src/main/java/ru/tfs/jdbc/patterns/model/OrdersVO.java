package ru.tfs.jdbc.patterns.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class OrdersVO {

    private Long order_id;
    private ClientVO client;
//    переделать на enum
    private String status;
    private String address;
//    private LocalDateTime creationTime;
//    private LocalDateTime endTime;
//    private LocalDateTime isDelete;
    private Date creationTime;
    private Date endTime;
    private Date isDelete;

    public Long getOrderId() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public ClientVO getClient() {
        return client;
    }

    public void setClient(ClientVO client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Date isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersVO ordersVO = (OrdersVO) o;
        return Objects.equals(order_id, ordersVO.order_id) && Objects.equals(client, ordersVO.client) && Objects.equals(status, ordersVO.status) && Objects.equals(address, ordersVO.address) && Objects.equals(creationTime, ordersVO.creationTime) && Objects.equals(endTime, ordersVO.endTime) && Objects.equals(isDelete, ordersVO.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id);
    }
}
