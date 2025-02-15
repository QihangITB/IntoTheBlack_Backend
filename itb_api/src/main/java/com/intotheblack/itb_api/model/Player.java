package com.intotheblack.itb_api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer id;

    @Column(name = "record_time")
    private String recordTime;

    @ElementCollection
    @Column(name = "fragment_list")
    private List<String> fragmentList;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

     // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public List<String> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<String> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
