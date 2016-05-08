package gengine.logic.exceptions;

@SuppressWarnings("serial")
public class ItJustKeepsGoingException extends Exception {
    public ItJustKeepsGoingException(String message) {
        super(message);
    }

    public ItJustKeepsGoingException() {
        super();
    }
}
