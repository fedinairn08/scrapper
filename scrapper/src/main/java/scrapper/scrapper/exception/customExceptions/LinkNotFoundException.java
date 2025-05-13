package scrapper.scrapper.exception.customExceptions;

public class LinkNotFoundException extends RuntimeException {
    public LinkNotFoundException(String url) {
        super("Ссылка " + url + " не найдена");
    }
}
