package com.draper.bankapi.controller.account;

public class OpenNewAccountRequest {
    private String passcode;

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}