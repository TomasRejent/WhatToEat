/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.data.exception;

/**
 *
 * @author Tomas Rejent
 */
public class DataSaveException extends RuntimeException{

    public DataSaveException() {
    }

    public DataSaveException(String message) {
        super(message);
    }

    public DataSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSaveException(Throwable cause) {
        super(cause);
    }
}
