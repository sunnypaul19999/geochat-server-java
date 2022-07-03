package helios.server.geochat.dto;

import java.io.Serializable;

public class InvalidDTOFieldValueException extends Exception {

    public static class ErrorView implements Serializable {
        private static final long serialVersionUID = 4L;

        private String message;

        public ErrorView(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public InvalidDTOFieldValueException() {
        super("Invalid field values");
    }

    public InvalidDTOFieldValueException(String errorMsg) {
        super(errorMsg);
    }

    public InvalidDTOFieldValueException.ErrorView getErrorView() {
        return new ErrorView("Hello");
    }
}
