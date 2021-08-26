package ru.tfs.jdbc.patterns.model;

import java.util.Objects;

public class RestaurantVO {

    private Long restaurant_id;
    private String name;
    private String address;
    private int rating;

    public Long getRestaurantId() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantVO that = (RestaurantVO) o;
        return rating == that.rating && Objects.equals(restaurant_id, that.restaurant_id) && Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant_id);
    }
}
