package statemachine.protocolexceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public abstract class BaseErrCodeException extends Exception {
    private int mErrCode;

    public BaseErrCodeException() {
        super("Unknown error");
        mErrCode = Integer.MIN_VALUE;
    }

    public BaseErrCodeException(String message, int errCode) {
        super(message);
        mErrCode = errCode;
    }

    public int getErrCode() {
        return mErrCode;
    }
}
