package strategies;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//Concrete strategy: Implements credit card payment method.

public class PayByCreditCard implements PayStrategy {
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private CreditCard card;


    //Collect credit card data from user input.
 
    @Override
    public void collectPaymentDetails() {
        try {
            System.out.print("Enter the card number: ");
            String number = READER.readLine();
            System.out.print("Enter the card expiration date 'mm/yy': ");
            String date = READER.readLine();
            System.out.print("Enter the CVV code: ");
            String cvv = READER.readLine();
            card = new CreditCard(number, date, cvv);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    //Process the payment. Assumes card has enough funds.
    @Override
    public boolean pay(int paymentAmount) {
        if (cardIsPresent()) {
            // Check if card has enough money (dummy check)
            if (card.getAmount() >= paymentAmount) {
                System.out.println("Paying " + paymentAmount + " using Credit Card.");
                card.setAmount(card.getAmount() - paymentAmount);
                return true;
            } else {
                System.out.println("Not enough funds on the card.");
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean cardIsPresent() {
        return card != null;
    }
}