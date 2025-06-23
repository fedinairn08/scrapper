package scrapper.scrapper.exception.customExceptions;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(final Long id) {
        super("Чат " + id + " не найден");
    }
}
