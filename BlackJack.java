// Array Group 3
// Abraham Gonzalez
// Johnny Mejia
// Krystal Rios
// Miguel Mendoza
// Date: 1/24/25

import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    private static final int[] DECK = new int[52];
    private static int currentCardIndex = 0;

    //Miguel Mendoza: Multiple Rounds
    private static int wins = 0;
    private static int loses = 0;
    private static int ties = 0;


    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Abraham Gonzalez 
        //It programs to handle the options or descisions
        
        boolean playAgain = true;
        
       while (playAgain) {

        initializeDeck();
        shuffleDeck();

        // Krystal : Multiplayer option: allows multiple human players (1-4) to play against dealer
        // Also ask for number of players
        System.out.print("Enter the number of players (1-4) : ");
         int numPlayers = scanner.nextInt();
         scanner.nextLine(); // Krystal: consumes newLine

         if (numPlayers < 1 || numPlayers > 4) {
            System.out.println("Invalid number of players. Please restart the game with 1-4 players.");
            return;
         }

         // Krystal: Array to store totals for players
         int[] playerTotals = new int[numPlayers]; // Krystal : Each player gets their own total

         //Krystal: deal initial cards to each player
         for ( int i = 0; i < numPlayers; i++){
            System.out.println("Player " + (i + 1) + " 's turn:");
            playerTotals[i] = dealInitialPlayerCards();
         }

         int dealerTotal = dealInitialDealerCards(scanner);

         // Miguel: Each players turns
         for ( int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + (i + 1) + "'s turn:");
            playerTotals[i] = playerTurn(scanner, playerTotals[i]);
            if (playerTotals[i] > 21) {
                System.out.println("Player " + (i + 1) + " busted!");
            }
         }
        // Miguel : Dealers turn
         dealerTotal = dealerTurn(dealerTotal);

         // Miguel : determines the winner for each player
         for ( int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + (i + 1) + "'s result:");
            determineWinner(playerTotals[i], dealerTotal);
         }
         // Krystal: Play again option
         System.out.println("Play another round? (y/n): ");
         String response = scanner.nextLine().toLowerCase();
         if (response.equals("n")) {
            playAgain = false;
            System.out.println("Thank you for playing!");
            System.out.println("Final Stats: Wns = " + wins + ", Ties = " + ties + ", Loses = " + loses);
             } 

         }
         scanner.close();
    } 

        int playerTotal = dealInitialPlayerCards();
        int dealerTotal = dealInitialDealerCards(scanner);

        playerTotal = playerTurn(scanner, playerTotal);
        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins.");
            loses++;
        } else {

        dealerTotal = dealerTurn(dealerTotal);
        determineWinner(playerTotal, dealerTotal);

       
    }

    // Abraham: If the player could press either y or n to play again
    System.out.println("Play another round? (y/n): ");
    String response = scanner.nextLine().toLowerCase();
    if(response.equals("n")) {
        playAgain = false;
        System.out.println(" Thank you for playing!");
        System.out.println(" Final Stats: Wins " + wins + ", Ties = " + ties + ", Loses = " + loses);
    }
}

scanner.close();
}


    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }

    private static void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < DECK.length; i++) {
            int index = random.nextInt(DECK.length);
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }
    }

    private static int dealInitialPlayerCards() {
        int card1 = dealCard();
        int card2 = dealCard();
        System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4] + " and "
                + RANKS[card2] + " of " + SUITS[card2 / 13]);
        return cardValue(card1) + cardValue(card2);
    }

    private static int dealInitialDealerCards(Scanner scanner) {
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4]);
        offerInsurance(scanner, card1);
        return cardValue(card1);
    }

    //Johnny Mejia
    //Insurance: If the dealer's face-up card is an Ace, offer the player insurance.

    private static void offerInsurance(Scanner scanner, int card) {
        if (RANKS[card].equals("Ace")) {
            System.out.println("Dealer's face-up card is an Ace. Do you want to take insurance? (yes/no)");
            String decision = scanner.nextLine().toLowerCase();
            if (decision.equals("yes")) {
                System.out.println("You have taken insurance.");
                // Implement insurance logic here if needed
            } else {
                System.out.println("You have declined insurance.");
            }
        }
    }

    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                System.out.println("You drew a " + RANKS[newCard] + " of " + SUITS[DECK[currentCardIndex] % 4]);
                if (playerTotal > 21) {
                    break;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }

    private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            int newCard = dealCard();
            dealerTotal += cardValue(newCard);
        }
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }

   
    private static void determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
            wins++;
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
            ties++;
        } else {
            System.out.println("Dealer wins!");
            loses++;
        }
        System.out.println("Current Status");
        System.out.println("Wins " + wins);
        System.out.println("Ties " + ties);
        System.out.println("Loses " + loses); 
    }


    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
    }

    private static int cardValue(int card) {
        return card < 9 ? card + 2 : 10;
    }

    int linearSearch(int[] numbers, int key) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
            }
        }
        return -1; // not found
    }
}
