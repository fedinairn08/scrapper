package scrapper.scrapper.exception.customExceptions;

public class LinkAlreadyExistsException extends RuntimeException {
    public LinkAlreadyExistsException(String url) {
        super("Ссылка " + url + " уже отслеживается");
    }
}
