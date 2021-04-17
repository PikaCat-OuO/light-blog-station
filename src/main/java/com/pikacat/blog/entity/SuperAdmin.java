package com.pikacat.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;

public class SuperAdmin {
    @TableId
    private String username;
    private String password;

    public SuperAdmin() {
    }

    public SuperAdmin(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "SuperAdmin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
