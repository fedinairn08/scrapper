package scrapper.scrapper.service.api;

import linkparser.linkparser.model.LinkParserResult;
import linkparser.linkparser.model.StackOverflowResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scrapper.scrapper.client.StackOverflowClient;
import scrapper.scrapper.dto.response.StackOverflowQuestionResponse;
import scrapper.scrapper.dto.stackoverflow.StackOverflowQuestion;
import scrapper.scrapper.entity.Link;

@Service
@RequiredArgsConstructor
public class StackOverflowApiService extends ApiService {

    private final StackOverflowClient stackOverflowClient;

    @Override
    public String checkUpdate(final LinkParserResult linkParserResult, final Link link) {
        if (linkParserResult instanceof StackOverflowResult stackOverflowResult) {
            StackOverflowQuestionResponse response = stackOverflowClient.getQuestionInfo(
                    stackOverflowResult.questionId()
            );
            StackOverflowQuestion question = response.items().getFirst();

            return "Обновление [вопроса](" +
                    stackOverflowResult.url() +
                    ")\n" +
                    "Последняя активность: " +
                    question.last_activity_date().toString() +
                    "\n" +
                    "Последнее обновление: " +
                    question.last_edit_date().toString() +
                    "\n" +
                    "На вопрос " +
                    (question.is_answered() ? "есть ответы" : "нет ответов") +
                    "\n";
        } else if (nextService != null) {
            return nextService.checkUpdate(linkParserResult, link);
        } else {
            return null;
        }
    }
}
