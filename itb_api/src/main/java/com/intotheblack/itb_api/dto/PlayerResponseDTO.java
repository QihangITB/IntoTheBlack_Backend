package com.intotheblack.itb_api.dto;

import java.util.List;

public class PlayerResponseDTO {

    private Integer id;
    private String recordTime;
    private List<FragmentDTO> fragmentList;
    private String user;

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

    public List<FragmentDTO> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<FragmentDTO> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}
