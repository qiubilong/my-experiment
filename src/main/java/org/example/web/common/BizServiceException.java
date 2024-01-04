package org.example.web.common;

public class BizServiceException extends RuntimeException {
    CommonResult result;

    public BizServiceException(CommonResult result) {
        super(result.getMessage());
        this.result = result;
    }

    public BizServiceException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.result = new CommonResult((long)errorCode.getCode(), errorCode.getMessage(), (Object)null);
    }

    public BizServiceException(String message) {
        super(message);
    }

    public BizServiceException(Throwable cause) {
        super(cause);
    }

    public BizServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizServiceException(IErrorCode code, String... args) {
        super(code.getMessage());
        this.result = new CommonResult((long)code.getCode(), String.format(code.getMessage(), args), (Object)null);
    }

    public BizServiceException(IErrorCode code, boolean noCode) {
        super(code.getMessage());
        this.result = new CommonResult((long)code.getCode(), code.getMessage(), noCode);
    }

    public CommonResult getResult() {
        return this.result;
    }
}
