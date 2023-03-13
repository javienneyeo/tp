package com.clanki.parser;


import com.clanki.commands.AddCommand;
import com.clanki.commands.ByeCommand;
import com.clanki.commands.Command;
import com.clanki.commands.DeleteCommand;
import com.clanki.commands.ReviewCommand;
import com.clanki.commands.UnknownCommand;
import com.clanki.exceptions.EmptyFlashcardAnswerException;
import com.clanki.exceptions.EmptyFlashcardQuestionException;
import com.clanki.exceptions.InvalidAddFlashcardInputException;

public class Parser {
    public static final String QUESTION_START_INDICATOR = "/q";
    public static final String ANSWER_START_INDICATOR = "/a";

    public Command parseCommand(String userInput) {
        String commandPhrase = userInput.split(" ")[0];
        switch (commandPhrase) {
        case "add":
            try {
                return reformatAddCommandInput(userInput);
            } catch (InvalidAddFlashcardInputException e) {
                System.out.println("The input is in an incorrect format, please follow the format in user guide");
            } catch (EmptyFlashcardQuestionException e) {
                System.out.println("The question of this card is empty, please enter one.");
            } catch (EmptyFlashcardAnswerException e) {
                System.out.println("The answer for this flashcard is empty, please enter one.");
            }
            break;
        case "del":
            int index = Integer.parseInt(userInput.split(" ")[1]);
            return new DeleteCommand(index);
        case "review":
            return new ReviewCommand();
        case "bye":
            return new ByeCommand();
        default:
            return new UnknownCommand();
        }
        return new UnknownCommand();
    }

    /**
     * Constructs an AddCommand from the input of the user, if the input is of an
     * incorrect format, a respective exception will be thrown.
     *
     * @param userInput The input collected by Ui from the user.
     * @return An AddCommand with the question and answer text extracted from user
     * input.
     * @throws InvalidAddFlashcardInputException If the start indicators cannot be
     *                                           found.
     * @throws EmptyFlashcardQuestionException   If the string is empty after
     *                                           QUESTION_START_INDICATOR.
     * @throws EmptyFlashcardAnswerException     If the string is empty after
     *                                           ANSWER_START_INDICATOR.
     */
    public Command reformatAddCommandInput(String userInput)
            throws InvalidAddFlashcardInputException, EmptyFlashcardQuestionException, EmptyFlashcardAnswerException {
        int positionOfStartOfQuestion = userInput.indexOf(QUESTION_START_INDICATOR);
        int positionOfStartOfAnswer = userInput.indexOf(ANSWER_START_INDICATOR);
        if (positionOfStartOfAnswer == -1 || positionOfStartOfQuestion == -1) {
            throw new InvalidAddFlashcardInputException();
        }
        String questionText = "";
        String answerText = "";
        if (positionOfStartOfAnswer > positionOfStartOfQuestion) {
            questionText = userInput
                    .substring(positionOfStartOfQuestion + QUESTION_START_INDICATOR.length(), positionOfStartOfAnswer)
                    .trim();
            answerText = userInput.substring(positionOfStartOfAnswer + ANSWER_START_INDICATOR.length()).trim();
        } else {
            questionText = userInput.substring(positionOfStartOfQuestion + QUESTION_START_INDICATOR.length()).trim();
            answerText = userInput
                    .substring(positionOfStartOfAnswer + ANSWER_START_INDICATOR.length(), positionOfStartOfQuestion)
                    .trim();
        }

        if (questionText.isEmpty()) {
            throw new EmptyFlashcardQuestionException();
        }
        if (answerText.isEmpty()) {
            throw new EmptyFlashcardAnswerException();
        }
        return new AddCommand(questionText, answerText);
    }
}
