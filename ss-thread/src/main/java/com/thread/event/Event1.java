package com.thread.event;


import org.springframework.context.ApplicationEvent;

/**
 * 定义自定义事件
 */

public class Event1 extends ApplicationEvent {
    private String data;
    private String msg;

    public Event1(Object source, String msg,  String data) {
        super(source);
        this.msg = msg;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
