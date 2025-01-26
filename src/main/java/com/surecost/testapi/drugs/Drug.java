package com.surecost.testapi.drugs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class Drug {
    private UUID uid;

    @NotEmpty(message = "The name of the drug cannot be empty")
    private String name;

    @NotEmpty(message = "The manufacturer of the drug cannot be empty")
    private String manufacturer;

    @NotNull(message = "The quantity of the drug cannot be null")
    @Positive(message = "The quantity of the drug must be positive")
    private Integer quantity;

    @NotNull(message = "The price of the drug cannot be null")
    @Positive(message = "The price of the drug must be positive")
    private Double price;

    public Drug(Double price, int quantity, String name, String manufacturer) {
        this.price = price;
        this.quantity = quantity;
        this.name = name;
        this.manufacturer = manufacturer;
        this.uid = UUID.randomUUID();
    }

    public UUID getUid() {
        return uid;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
