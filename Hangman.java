
/**
 * RandomPasswordGenerator is a program that randomly generates a word and prompts the user to guess one 
 * letter at a time to fill in the word
 *
 * @author Ruvarashe Sadya
 * @version 1.0
*/

import java.util.Random;
import java.util.Scanner;

public class Hangman {
    private static Scanner scanner; // So it's accessible by all methods

    /**
     * The entry point of the program.
     * 
     * @param args The command-line arguments containing optional list of words to
     *             guess from.
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        String[] wordArray;
        // If there are no command line arguments, default to list of fruit
        if (args.length == 0) {
            wordArray = new String[] { "apple", "pear", "banana", "orange" };
        } else {
            wordArray = args;
        }

        String word = selectWord(wordArray); // Select a random word in array to guess
        int lengthOfWord = word.length();
        int lives = 10; // Number of incorrect attempts allowed so game ends at some point, per
                        // traditional Hangman rules
        String stars = createStars(lengthOfWord); // Create placeholder stars for word based on it's length
        int countMissed = 0; // Set counter for missed guesses to 0

        playGame(stars, word, lives, countMissed); // Play Game at least once

        // Play game again, asking user at least once
        boolean playAgain = true;
        do {
            
            System.out.println("Do you want to guess another word? Enter y or n");
            String ans = scanner.nextLine(); // User answer

            if (ans.equals("y")) {
                String[] newWordArray = updateArray(wordArray, word);
                playGameAgain(word, newWordArray, lives); // Play the game again, with the word already played removed
            } else if (ans.equals("n")) {
                playAgain = false;
            } else {
                System.out.println("Enter y or n"); // If they enter an invalid input
            }
        } while (playAgain);

        scanner.close();
    }

    /**
     * Selects a random word from the given array of words.
     * 
     * @param wordArray An array of words to choose from.
     * @return The randomly selected word.
     */
    public static String selectWord(String[] wordArray) {
        // Select random word from list
        Random random = new Random();
        int wordIndex = random.nextInt(wordArray.length); // Random index in the length of array of word
        String word = wordArray[wordIndex];
        word = word.toLowerCase(); // if user enters args, the game can still work
        return word;
    }

    /**
     * Creates a string of asterisks to represent hidden letters in the word.
     * 
     * @param lengthOfWord The length of the word to be represented.
     * @return A string of asterisks with the same length as the word.
     */
    public static String createStars(int lengthOfWord) {
        String stars = "";
        for (int i = 0; i < lengthOfWord; i++) { // Append the empty string of stars with stars count (length of word)
            stars += "*";
        }
        return stars;
    }

    /**
     * Prompts the user to enter a guess for a letter in the word.
     * 
     * @param stars The current representation of the word with hidden letters.
     * @return The user's guessed letter.
     */
    public static String getGuess(String stars) {
        
        String guess;
        // Make the user enter only one letter at a time. IN FUTURE VERSIONS: Allow more
        // chars at a time so user can guess on first attempt
        do {
            System.out.println("(Guess) Enter a letter in word " + stars);
            guess = scanner.nextLine();
            if (guess.length() != 1) {
                System.out.println("Please enter only one letter at a time!");
            }
        } while (guess.length() != 1);
        return guess;
    }

    /**
     * Generates a congratulatory message indicating the correct word and the number
     * of missed guesses.
     * 
     * @param word        The correct word.
     * @param countMissed The number of missed guesses.
     * @return The congratulatory message.
     */
    public static String congrats(String word, int countMissed) {
        if (countMissed == 1) {
            return "The word is " + word + ". You missed " + countMissed + " time";
        } else {
            return "The word is " + word + ". You missed " + countMissed + " times";
        }
    }

    /**
     * Updates the representation of the word with correctly guessed letters.
     * 
     * @param word  The correct word.
     * @param stars The current representation of the word with hidden letters.
     * @param guess The letter guessed by the user.
     * @return The updated representation of the word with correctly guessed letters
     *         revealed.
     */
    public static String updateStars(String word, String stars, char guess) {
        // Replaces the stars with the correctly guessed character
        char[] updatedStars = stars.toCharArray(); // converts stars string to an array of characters so i can just
                                                   // replace char at index
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) { // reason why guess has to come in as a character
                updatedStars[i] = guess;
            }
        }
        return new String(updatedStars); // conversion of the updated stars to a string
    }

    /**
     * Updates the representation of the word with correctly guessed letters.
     * 
     * @param word        The correct word.
     * @param stars       The current representation of the word with hidden
     *                    letters.
     * @param lives       The number of misses the user gets to make before game
     *                    ends
     * @param countMissed missed letters set to 0
     * @return The updated representation of the word with correctly guessed letters
     *         revealed.
     */
    public static void playGame(String stars, String word, int lives, int countMissed) {
        while (lives > 0) {

            String guess = getGuess(stars); // get the guess and prompt the users with the stars in place
            if (word.contains(guess)) {
                // Check if char already shown in stars
                if (stars.contains(guess)) {
                    System.out.println(guess + " is already in word");
                    lives -= 1; // Reduce number of lives because they failed to do it correctly
                }
                // Else reveal it
                else {
                    stars = updateStars(word, stars, guess.charAt(0)); // so that the updatedStars method can work, I
                                                                       // retrieved the char with .charAt()
                    // Check again if the guess is correct
                    if (stars.equals(word)) {
                        System.out.println(congrats(word, countMissed));
                        break;
                    }
                }
            }

            // If the guess is not in the word...
            else {
                countMissed += 1; // track misses
                lives -= 1; // reduce lives
                System.out.println(guess + " is not in the word");
                // End game if lives are exhausted
                if (lives == 0) {
                    System.out.println(congrats(word, countMissed));
                }
            }
        }
    }

    /**
     * plays the game again with an updated list of words, having removed the just
     * played
     * 
     * @param word      The already guessed word and correct word
     * @param wordArray the just played list of words
     * 
     */
    public static void playGameAgain(String word, String[] wordArray, int lives) {
        word = selectWord(wordArray); // select a random word in this new array and play again
        int lengthOfWord = word.length();
        int countMissed = 0;
        // Create placeholder stars for word
        String stars = createStars(lengthOfWord);
        playGame(stars, word, lives, countMissed);
    }

    /**
     * Updates the input array of words by removing the specified word.
     * Creates a new array with all words from the input array except for the
     * specified word.
     *
     * @param wordArray The original array of words.
     * @param word      The word to be removed from the array.
     * @return A new array containing all words from the input array except for the
     *         specified word.
     */
    public static String[] updateArray(String[] wordArray, String word) {
        // new array to add new words if they haven't been played
        String[] newWordArray = new String[wordArray.length - 1];
        for (int i = 0, k = 0; i < wordArray.length; i++) {
            if (wordArray[i] != word) { // only putting word that are not the one just played
                newWordArray[k] = wordArray[i];
                k++;
            }
        }
        return newWordArray;
    }
}
