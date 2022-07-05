package helios.server.geochat.controller.exceptions;

import java.io.Serializable;

public class GeoPointRestRegistrationFailure extends GeoPointRestException {

    public static class ErrorView implements Serializable {
        private static final long serialVersionUID = 4L;

        private boolean registrationStatus;
        private String message;

        private ErrorView() {
            this.registrationStatus = false;
        }

        public ErrorView(String message) {
            this();
            this.message = message;
        }

        public boolean getRegistrationStatus() {
            return registrationStatus;
        }

        public String getMessage() {
            return message;
        }
    }

    public GeoPointRestRegistrationFailure(String message) {
        super(message);
    }

}
