package scrapper.scrapper.exception.customExceptions;

public class DuplicateChatException extends RuntimeException {
    public DuplicateChatException(final Long id) {
        super("Чат " + id + " уже зарегистрирован");
    }
}
