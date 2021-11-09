package com.Pos10Max.POS10APIMAX.UiResponse;

import org.springframework.hateoas.RepresentationModel;

public class LoginSuccessResponse extends RepresentationModel<ItemResponse> {

    private String usertype;
    private String message;
    private String userId;

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
