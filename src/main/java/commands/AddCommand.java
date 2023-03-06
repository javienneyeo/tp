package commands;

import objects.FlashcardList;
import ui.Ui;

/**
 * A type of command that will instruct to add a new element into the ArrayList
 * of flashcards.
 */
public class AddCommand extends Command{
    String questionText;
    String answerText;

    public AddCommand(String questionText, String answerText) {
        this.questionText = questionText;
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "Question to add: " + questionText + " Answer: " + answerText;
    }

    @Override
    public void execute(FlashcardList flashcardList, Ui display) {
        flashcardList.addNewFlashcard(questionText,answerText);
        display.printSuccessfulAddMessage(questionText,answerText);
    }

}
