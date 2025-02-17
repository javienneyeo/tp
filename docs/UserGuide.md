# Clanki User Guide

Clanki is a command-line interface (CLI) application for managing
flashcards. It allows users to create, manage and study flashcards
to assist them with memorisation by using a technique called
[spaced repetition](https://en.wikipedia.org/wiki/Spaced_repetition).

## Quick start

1. Ensure you have Java 11 or above installed.
2. Download the latest version of Clanki from [here](https://github.com/AY2223S2-CS2113-T15-4/tp/releases/tag/v1.0).
3. Copy the file to the folder you want to use as the home folder for Clanki.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clanki.jar`
   command to run the application.
   You should be greeted with `Welcome to Clanki! Time to start studying!` after a few seconds.
5. Type the command in the command box and press Enter to execute it.
   Some example commands you can try:
    - `add /q What is the worst fruit? /a Durian`: Add a flashcard with Question: What is the worst fruit?
      and Answer: Durian to the list of flashcards.
    - `review`: Go through the flashcards that are due today.
    - `bye`: Exit the app.
6. Refer to the Features below for details of each command.

## Features

> Square brackets indicate optional sections of the symtax.

### Adding a flashcard

```
add /q QUESTION /a ANSWER
```

- `QUESTION` will be displayed when reviewing. For how the
  reviewing process works, see "Review flashcards" section below.

#### Example

```
> add /q What is the worst fruit? /a Durian
You have added the following card:
Q: What is the worst fruit?
A: Durian
```

### Review flashcards

```
review
```

- Flashcards that are due on the day will be displayed one after
  another, queued in a randomised order.

- When the user has recalled the answer for a card, or has given
  up on doing so, they can then press <kbd>Enter</kbd>.

- The app will then show the answer and ask the user if they got
  it right. They can then type `y`/`n` to indicate that they remembered
  the answer correctly, or they couldn't remember the correct answer,
  respectively.

      * If `y` is indicated, the card is then set to a new due date

  in the future.

      * Otherwise, the card is pushed back to the today's

  queue for reviewing later, until the user can get it correct. When
  the user has got it correct, the new date is set to tomorrow.

- Then the review session continues with the next card in the queue.

#### Example

```
> review
There are 10 cards available for review today.
---
Q: What is the worst fruit? (ENTER to show answer)
A: Durian
Did you get it right? (y/n) n
The card will be asked later today.
---
Q: What is the Japanese word for "fruit"? (ENTER to show answer)
A: 果物
Did you get it right? (y/n) y
The card will be asked on 20 February 2023
---
Q: What is the worst fruit? (ENTER to show answer)
A: Durian
Did you get it right? (y/n) y
The card will be asked tomorrow.
---
You have finished reviewing for today. Congratulations.
```

### Update flashcards

```
update /q QUERY
```

- `QUERY` can be part of the card's question or answer.

- A list of cards that contain this query will be listed, with an
  index assigned to each.

- The user can then type the index of the card they wish to update
  with the following syntax

  ```
  INDEX [/q NEW_QUESTION] [/a NEW_ANSWER] [/d NEW_DUE_DATE]
  ```

#### Example

```
> update fruit
Found 2 cards with the query "fruit":
[1]
Q: What is the worst fruit?
A: Durian
D: 2023-02-29
[2]
Q: What is the Japanese word for "fruit"?
A: 果物
D: 2023-02-29
Which flashcard do you want to update? 1 /q What is the best fruit?
Understood. The card has been updated to
Q: What is the best fruit?
A: Durian
D: 2023-02-29
```

### Delete flashcards

```
delete /q QUERY
```

- `QUERY` can be part of the card's question or answer.

- A list of cards that contain this query will be listed, with an
  index assigned to each.

- The user can then type the index of the card they wish to delete.
  The card with that index (on that list) is then deleted.

#### Example

```
> delete fruit
Found 2 cards with the query "fruit":
[1] Q: What is the best fruit?
    A: Durian
[2] Q: What is the Japanese word for "fruit"?
    A: 果物
Which one do you want to delete? 2
Understood. The card has been deleted.
```

### Exit program

```
bye
```

Exits the program.
