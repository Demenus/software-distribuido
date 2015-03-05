package statemachine;

import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aaron on 24/02/2015.
 */
public interface StateNode {
    public String getState();

    /**
     * Method to get the information of each request.
     * @param messageStream
     * @return
     * @throws ParseException
     * @throws ReadException 
     */
    Object parseRequestBody(InputStream messageStream) throws ParseException, ReadException;

    /**
     * Method to check if the previous state is correct.
     * @param previousState
     * @throws StateException 
     */
    void checkPreviousState(String previousState) throws StateException;

    /**
     * Method to verify the functionality of the game.
     * @param previousState
     * @param controller
     * @param parsedMessage
     * @return
     * @throws ApplicationException 
     */
    Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException;

    /**
     * Method to response the request if everyting is ok.
     * @param responseStream
     * @param response
     * @throws WriteException 
     */
    void onSuccess(OutputStream responseStream, Object response) throws WriteException;

    /**
     * Method to notify to user if the game has error.
     * @param responseStream
     * @param errCode
     * @param message
     * @throws WriteException 
     */
    public void onError(OutputStream responseStream, ErrType errCode, String message) throws WriteException;
}
