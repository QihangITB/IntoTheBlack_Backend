package com.intotheblack.itb_api.model;

import jakarta.persistence.*;

@Entity
public class Fragment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fragment_id")
    private Long fragmentId;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    // Getters y Setters
    public Long getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(Long fragmentId) {
        this.fragmentId = fragmentId;
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
