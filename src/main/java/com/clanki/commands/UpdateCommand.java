package com.clanki.commands;

import com.clanki.exceptions.InvalidInputException;
import com.clanki.objects.Flashcard;
import com.clanki.objects.FlashcardList;
import com.clanki.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UpdateCommand extends Command {
    String query;
    ArrayList<Flashcard> matchingFlashcards = new ArrayList<>();

    public UpdateCommand(String query) {
        super();
        this.query = query;
    }

    public void printFlashCard(Flashcard flashcard) {
        System.out.println("Q: " + flashcard.getQuestion());
        System.out.println("A: " + flashcard.getAnswer());
//        System.out.println("D: " + flashcard.getDueDate());
    }

    public void printFlashCards(ArrayList<Flashcard> flashcards) {
        for (int i = 0; i < flashcards.size(); i++) {
            System.out.println("[" + (i + 1) + "]");
            printFlashCard(flashcards.get(i));
        }
    }

    public void findFlashcard(ArrayList<Flashcard> flashcards, String query) {
        for (int i = 0; i < flashcards.size(); i++) {
            Flashcard currentFlashcard = flashcards.get(i);
            if (currentFlashcard.getQuestion().toLowerCase().contains(query.toLowerCase())
                    || currentFlashcard.getAnswer().toLowerCase().contains(query.toLowerCase()) ||
                    currentFlashcard.getDueDate().toString().equals(query)) {
                matchingFlashcards.add(currentFlashcard);
            }
        }
    }

    public int implementUpdate(ArrayList<Flashcard> flashcards, String userText) throws InvalidInputException {
        String[] userTexts = userText.split(" ", 3);
        int indexInMatchList = Integer.parseInt(userTexts[0]) - 1;
        Flashcard flashcardToChange = matchingFlashcards.get(indexInMatchList);
        int index = flashcards.indexOf(flashcardToChange);
        if (!userTexts[1].equals("/a") && !userTexts[1].equals("/q") && !userTexts[1].equals("/d")) {
            throw new InvalidInputException();
        }
        if (userTexts[1].contains("q")) {
            flashcards.get(index).setQuestion(userTexts[2].substring(0, 1).toUpperCase()
                    + userTexts[2].substring(1));
        }
        if (userTexts[1].contains("a")) {
            flashcards.get(index).setAnswer(userTexts[2].substring(0, 1).toUpperCase()
                    + userTexts[2].substring(1));
        }
        if (userTexts[1].contains("d")) {
            String date = userTexts[2];
            LocalDate dateToChange = LocalDate.parse(date);
            flashcards.get(index).setDueDate(dateToChange);
        }
        return index;
    }

    @Override
    public void execute(FlashcardList flashcardList, Ui display) {
        ArrayList<Flashcard> flashcards = flashcardList.getFlashCards();
        findFlashcard(flashcards, query);
        if (matchingFlashcards.size() > 0) {
            System.out.println(
                    "Found " + matchingFlashcards.size() + " cards with the query \"" + query + "\":");
            printFlashCards(matchingFlashcards);
            System.out.println("Which flashcard do you want to update?");
            String userText = display.getUserCommand();
            int index = 0;
            try {
                index = implementUpdate(flashcards, userText);
                System.out.println("Understood. The card has been updated to");
                printFlashCard(flashcards.get(index));
            } catch (ArrayIndexOutOfBoundsException | InvalidInputException e) {
                System.out.println("Please enter the input in the correct format as shown in the user guide.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter the index of the flashcard you want to update.");
            } catch (IndexOutOfBoundsException e) {
                System.out.print("You have selected an index out of the list. ");
                System.out.println("There are only " + matchingFlashcards.size() + " flashcards that match the query.");
            } catch (DateTimeParseException e) {
                System.out.println("Please enter the date in the format: yyyy-mm-dd");
            }
        } else {
            System.out.println("There are no flashcards with the query \"" + query + "\".");
        }
    }
}
