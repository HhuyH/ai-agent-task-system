package com.huy.client.api;

import com.huy.client.exception.UnauthorizedException;

public class ApiExceptionHandler {

    public static RuntimeException handle(int statusCode) {

        return switch (statusCode) {

            case 400 ->
                    new RuntimeException("Dữ liệu không hợp lệ!");

            case 401 ->
                    new UnauthorizedException("Phiên đăng nhập đã hết hạn!");

            case 403 ->
                    new RuntimeException("Bạn không có quyền thực hiện!");

            case 404 ->
                    new RuntimeException("Không tìm thấy dữ liệu!");

            case 500 ->
                    new RuntimeException("Server đang gặp sự cố!");

            default ->
                    new RuntimeException(
                            "Có lỗi xảy ra! Mã lỗi: " + statusCode
                    );
        };
    }
}