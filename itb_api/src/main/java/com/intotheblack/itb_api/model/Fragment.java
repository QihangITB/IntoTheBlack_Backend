package com.intotheblack.itb_api.model;

import jakarta.persistence.*;

@Entity
@Table(name="fragment")
public class Fragment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fragment_id")
    private Integer id;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "message", nullable = false)
    private String message;

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
