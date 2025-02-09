package com.intotheblack.itb_api.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Long collectionId;

    @Column(name = "fragment_list", nullable = false)
    private List<Integer> fragments;

    // Getters y Setters
    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public List<Integer> getFragmentList() {
        return fragments;
    }

    public void setFragmentList(List<Integer> fragments) {
        this.fragments = fragments;
    }
}
