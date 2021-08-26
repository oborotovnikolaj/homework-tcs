package ru.tfs.jdbc.patterns.model;

import java.util.Objects;

public class ClientVO {

    private Long client_id;
    private String name;
    private String address;
    private String email;
    private String password;

    public Long getClientId() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientVO clientVO = (ClientVO) o;
        return Objects.equals(client_id, clientVO.client_id) && Objects.equals(name, clientVO.name) && Objects.equals(address, clientVO.address) && Objects.equals(email, clientVO.email) && Objects.equals(password, clientVO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id);
    }
}
