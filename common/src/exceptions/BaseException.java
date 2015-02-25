package exceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public class BaseException extends Exception {

    private ErrType mErrType;

    public BaseException(ErrType errType, String message) {
        super(message);
        mErrType = errType;
    }

    public ErrType getErrType() {
        return mErrType;
    }
}
