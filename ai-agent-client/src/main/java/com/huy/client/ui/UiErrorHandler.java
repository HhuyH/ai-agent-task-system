package com.huy.client.ui;

import java.net.ConnectException;

public class UiErrorHandler {

    public static String getMessage(Exception ex) {

        ex.printStackTrace();

        if (ex instanceof ConnectException) {
            return "Không thể kết nối tới server!";
        }

        String message = ex.getMessage();

        if (message == null || message.isBlank()) {
            return "Có lỗi xảy ra!";
        }

        return message;
    }
}