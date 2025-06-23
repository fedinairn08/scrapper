package scrapper.scrapper.exception.customExceptions;

public class LinkAlreadyExistsException extends RuntimeException {
    public LinkAlreadyExistsException(final String url) {
        super("Ссылка " + url + " уже отслеживается");
    }
}
