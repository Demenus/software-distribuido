package exceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public abstract class ServerException extends Exception {

    private ErrType mErrType;

    public ServerException(ErrType errType, String message) {
        super(message);
        mErrType = errType;
    }

    public ErrType getErrType() {
        return mErrType;
    }
}
