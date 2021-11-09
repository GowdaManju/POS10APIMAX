package com.Pos10Max.POS10APIMAX.UiRequest;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

//@JsonIgnoreProperties({ "id", "password", })
public class UserRequest {

    private String userId;

    private String usertype;

    private String username;

    private String password;


    @JsonFormat(pattern = "yyyy-MM-dd",shape= JsonFormat.Shape.STRING)
    private Date last;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }




    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
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

    public Date getLast() {
        return last;
    }

    public void setLast(Date last) {
        this.last = last;
    }

}
