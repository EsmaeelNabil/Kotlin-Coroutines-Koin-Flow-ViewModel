package com.esmaeel.anim.Base;

import java.util.ArrayList;

public class ErrorModel {

    private boolean status;
    private ArrayList<String> msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<String> getMsg() {
        return msg;
    }

    public void setMsg(ArrayList<String> msg) {
        this.msg = msg;
    }
}
