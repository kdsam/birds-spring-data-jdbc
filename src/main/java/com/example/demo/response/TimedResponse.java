package com.example.demo.response;

import java.io.Serializable;

public class TimedResponse<T> implements Serializable {

    private T data;
    private String status;
    private String startThread;
    private String endThread;
    private String requestId;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartThread() {
        return startThread;
    }

    public void setStartThread(String startThread) {
        this.startThread = startThread;
    }

    public String getEndThread() {
        return endThread;
    }

    public void setEndThread(String endThread) {
        this.endThread = endThread;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
