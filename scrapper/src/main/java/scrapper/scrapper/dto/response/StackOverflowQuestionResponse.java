package scrapper.scrapper.dto.response;

import scrapper.scrapper.dto.stackoverflow.StackOverflowQuestion;

import java.util.List;

public record StackOverflowQuestionResponse(List<StackOverflowQuestion> items) {
}
