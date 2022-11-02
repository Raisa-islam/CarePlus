package com.raisa.update1.object;

public class UpdateObject {
    String Id, update_msg;

    public UpdateObject(String id, String update_msg) {
        Id = id;
        this.update_msg = update_msg;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUpdate_msg() {
        return update_msg;
    }

    public void setUpdate_msg(String update_msg) {
        this.update_msg = update_msg;
    }

    public UpdateObject(){}
}
