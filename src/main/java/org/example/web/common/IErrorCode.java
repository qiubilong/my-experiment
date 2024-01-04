package org.example.web.common;

public interface IErrorCode {

    /**
     * 状态码
     * @return Integer
     */
    Integer getCode();

    /**
     * 消息内容
     * @return String
     */
    String getMessage();

}