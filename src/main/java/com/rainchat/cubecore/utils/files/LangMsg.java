package com.rainchat.cubecore.utils.files;

public class LangMsg {

    private LangTemplate template;
    private String msgDefault;
    private String message;
    private String path;

    public LangMsg(LangTemplate template, String path, String msgDefault) {
        this.template = template;
        this.path = path;
        this.msgDefault = msgDefault;
    }

    public LangTemplate getTemplate() {
        return template;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTemplate(LangTemplate template) {
        this.template = template;
    }

    public String getMsgDefault() {
        return msgDefault;
    }

    public void setMsgDefault(String msgDefault) {
        this.msgDefault = msgDefault;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
