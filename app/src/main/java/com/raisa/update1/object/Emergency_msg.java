package com.raisa.update1.object;

public class Emergency_msg {
    String id, msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Emergency_msg(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public Emergency_msg() {

    }


}
