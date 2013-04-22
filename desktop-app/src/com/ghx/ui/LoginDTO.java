package com.ghx.ui;

/**
 * User: ghaxx
 * Date: 22/04/2013
 * Time: 15:15
 */
public class LoginDTO {
    public String api_token;
    public String message;

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
