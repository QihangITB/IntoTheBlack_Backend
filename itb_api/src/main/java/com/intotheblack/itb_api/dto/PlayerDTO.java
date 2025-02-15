package com.intotheblack.itb_api.dto;

import java.util.List;

public class PlayerDTO {
    
    private String recordTime;
    private List<String> fragmentList;
    private String username;

    // Getters y setters
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

    public void addFragment(String fragment) {
        this.fragmentList.add(fragment);
    }

    public void removeFragment(String fragment) {
        this.fragmentList.remove(fragment);
    }

    public void cleanFragmentList() {
        this.fragmentList.clear();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
