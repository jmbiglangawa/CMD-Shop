package exception;

public class QuitTriggerException extends Exception {
    public static String message = "A quit command was triggerred by the user";

    public QuitTriggerException() {
        super(message);
    }

}
