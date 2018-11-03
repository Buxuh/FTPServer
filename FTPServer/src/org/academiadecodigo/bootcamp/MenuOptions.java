package org.academiadecodigo.bootcamp;

public enum  MenuOptions {

    VIEW_LIST(1, Message.VIEW_LIST),
    UPLOAD(2, Message.UPLOAD),
    DOWNLOAD(3, Message.DOWNLOAD);


    private int options;
    private String message;

    MenuOptions(int options, String message) {
        this.options = options;
        this.message = message;
    }

    public int getOptions() {
        return options;
    }

    public String getMessage() {
        return message;
    }
}
