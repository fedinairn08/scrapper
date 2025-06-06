package scrapper.scrapper.service.api;

import lombok.RequiredArgsConstructor;
import scrapper.scrapper.client.StackOverflowClient;
import scrapper.scrapper.dto.LinkData;
import scrapper.scrapper.dto.response.StackOverflowQuestionResponse;
import scrapper.scrapper.dto.stackoverflow.LinkDataStackOverflow;
import scrapper.scrapper.dto.stackoverflow.StackOverflowQuestion;
import scrapper.scrapper.enums.Site;

@RequiredArgsConstructor
public class StackOverflowApiService extends ApiService {

    private final StackOverflowClient stackOverflowClient;

    @Override
    public String checkUpdate(LinkData linkData) {
        if (linkData.getSite().equals(Site.STACK_OVERFLOW)) {
            LinkDataStackOverflow linkDataStackOverflow = (LinkDataStackOverflow) linkData;

            StackOverflowQuestionResponse response = stackOverflowClient.getQuestionInfo(
                    linkDataStackOverflow.getId()
            );
            StackOverflowQuestion question = response.items().get(0);

            return "Обновление [вопроса](" +
                    linkDataStackOverflow.getUrl() +
                    ")\n" +
                    "Последняя активность: " +
                    question.last_activity_date().toString() +
                    "\n" +
                    "Последний обновление: " +
                    question.last_edit_date().toString() +
                    "\n" +
                    "На вопрос " +
                    (question.is_answered() ? "есть ответы" : "нет ответов") +
                    "\n";
        } else if (nextService != null) {
            return nextService.checkUpdate(linkData);
        } else {
            return null;
        }
    }
}
