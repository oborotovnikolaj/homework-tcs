package ru.tfs.jdbc.patterns.model;

import java.util.Objects;

public class MenuItemVO {

    private Long menuitem_id;
    private RestaurantVO restaurant;
    private String name;
    private Integer cost;

    public Long getMenuitemId() {
        return menuitem_id;
    }

    public void setMenuitem_id(Long menuitem_id) {
        this.menuitem_id = menuitem_id;
    }

    public RestaurantVO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantVO restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemVO that = (MenuItemVO) o;
        return Objects.equals(menuitem_id, that.menuitem_id) && Objects.equals(restaurant, that.restaurant) && Objects.equals(name, that.name) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuitem_id);
    }
}
