package ru.tfs.jdbc.patterns.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class MenuItemToOrderVO {
    private Long id;
    private OrdersVO order;
    private MenuItemVO menuItem;
    private Integer quantity;
//    private LocalDateTime isDelete;
    private Date isDelete;

    public OrdersVO getOrder() {
        return order;
    }

    public void setOrder(OrdersVO order) {
        this.order = order;
    }

    public MenuItemVO getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemVO menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        MenuItemToOrderVO that = (MenuItemToOrderVO) o;
        return Objects.equals(id, that.id) && Objects.equals(order, that.order) && Objects.equals(menuItem, that.menuItem) && Objects.equals(quantity, that.quantity) && Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
