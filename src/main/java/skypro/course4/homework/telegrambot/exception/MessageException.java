package skypro.course4.homework.telegrambot.exception;

public class MessageException extends RuntimeException {
    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }
}