package com.fuchen.SaleTicketSystem;

import lombok.Data;

/**
 * 用户信息类
 * 包含用户的各种属性，用于判断适用的折扣策略
 */
@Data
public class UserInfo {
    private String name;
    private int age;
    private boolean isStudent;
    private boolean isVip;
    private boolean isMilitary;
    private int vipPoints; // VIP积分
    
    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
        this.isStudent = false;
        this.isVip = false;
        this.isMilitary = false;
        this.vipPoints = 0;
    }
    
    public void addVipPoints(int points) {
        this.vipPoints += points;
    }
    
    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isStudent=" + isStudent +
                ", isVip=" + isVip +
                ", isMilitary=" + isMilitary +
                ", vipPoints=" + vipPoints +
                '}';
    }
}