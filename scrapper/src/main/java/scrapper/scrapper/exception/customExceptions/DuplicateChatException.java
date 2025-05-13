package scrapper.scrapper.exception.customExceptions;

public class DuplicateChatException extends RuntimeException {
    public DuplicateChatException(Long id) {
        super("Чат " + id + " уже зарегистрирован");
    }
}
