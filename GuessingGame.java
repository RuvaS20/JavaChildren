import java.util.Scanner;
import java.util.Random;

public class GuessingGame {
    public static void main(String[] args)  {
        int seed = 5;
        Random random = new Random(seed);   // Set the seed for consistent testing, uses new instance in java
        int randomValue = random.nextInt(100) + 1;
        guessGame(randomValue);
    } 

    public static void guessGame(long seed) {
        
        System.out.println("Welcome to the Guessing Game!");
        System.out.println("I'm thinking of a number between 1 and 100. Can you guess it?");
        int count = 1;           // counting the number of tries, will be at least 1
        
        //Looping the guessing until it's right
        Scanner scanner = new Scanner(System.in);      //Accepting guess from user
        while (true) {
            
            System.out.print("Guess: ");
            long guess = scanner.nextLong();
            

            if (guess == seed && count == 1) {
                System.out.println("Yes! You guessed correctly after 1 try! Congratulations.");
                break;
            } else {
                if (guess > seed) {
                    System.out.println("Good try, but that's too high. Try again");
                } else if (guess < seed) {
                    System.out.println("Good try, but that's too low. Try again");
                } else {
                    System.out.println("Yes! You guessed correctly after " + count + " tries! Congratulations.");
                    break;
                }

                count += 1;
            }
        }
        scanner.close();
    }

}
