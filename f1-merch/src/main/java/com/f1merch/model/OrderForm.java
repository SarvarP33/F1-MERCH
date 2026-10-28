package com.f1merch.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderForm {

    @NotBlank(message = "Укажите имя")
    @Size(min = 2, max = 60, message = "Имя: от 2 до 60 символов")
    private String fullName;

    @NotBlank(message = "Укажите email")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "Укажите телефон")
    @Pattern(regexp = "^\\+?[0-9 ()-]{10,18}$", message = "Некорректный номер телефона")
    private String phone;

    @NotBlank(message = "Укажите город")
    private String city;

    @NotBlank(message = "Укажите адрес")
    @Size(min = 5, message = "Адрес слишком короткий")
    private String address;

    private String comment;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
