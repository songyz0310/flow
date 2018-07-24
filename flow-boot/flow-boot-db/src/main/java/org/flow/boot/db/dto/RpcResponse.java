package org.flow.boot.db.dto;

public class RpcResponse {
    private int ecode = 0;

    private String reason = "OK";

    private Object result;

    public RpcResponse() {
    }

    public RpcResponse(Object result) {
        this.result = result;
    }

    public RpcResponse(int ecode, String reason) {
        this.ecode = ecode;
        this.reason = reason;
    }

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RpcResponse [ecode=" + ecode + ", reason=" + reason + ", result=" + result + "]";
    }
}
