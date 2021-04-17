package com.pikacat.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class UserInfo {
    @TableId
    private String username;
    private String password;
    private String realname;
    private String gender;
    private Short age;
    private Date birthday;
    private String telephone;
    private String address;
    private String description;

    public UserInfo() {
    }

    public UserInfo(String username, String password, String realname, String gender, Short age, Date birthday, String telephone, String address, String description) {
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.telephone = telephone;
        this.address = address;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
