package com.bokky.extensionblocker.exception;

/**
 * 최대 커스텀 확장자 개수 초과 시 발생하는 예외입니다.
 */
public class MaxExtensionLimitException extends RuntimeException {

    /**
     * 기본 메시지 생성자
     */
    public MaxExtensionLimitException() {
        super("최대 커스텀 확장자 개수(200개)를 초과했습니다.");
    }

    /**
     * 커스텀 메시지 생성자
     * @param message 사용자 정의 메시지
     */
    public MaxExtensionLimitException(String message) {
        super(message);
    }
}
