
import order.Order;
import strategies.PayByCreditCard;
import strategies.PayByPayPal;
import strategies.PayStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


//Client code where the strategy is chosen and used.

public class Main {
    private static Map<Integer, Integer> priceOnProducts = new HashMap<>();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Order order = new Order();
    private static PayStrategy strategy; // The Strategy object

    // Initial product prices
    static {
        priceOnProducts.put(1, 2200); // Mother board
        priceOnProducts.put(2, 1850); // CPU
        priceOnProducts.put(3, 1100); // HDD
        priceOnProducts.put(4, 890);  // Memory
    }

    public static void main(String[] args) throws IOException {
        while (!order.isClosed()) {
            int cost;
            String continueChoice;

            // 1. Select products
            do {
                System.out.print("Please, select a product:" + "\n" +
                        "1 - Mother board (2200)" + "\n" +
                        "2 - CPU (1850)" + "\n" +
                        "3 - HDD (1100)" + "\n" +
                        "4 - Memory (890)" + "\n");
                int choice = Integer.parseInt(reader.readLine());
                cost = priceOnProducts.get(choice);
                System.out.print("Count: ");
                int count = Integer.parseInt(reader.readLine());
                order.setTotalCost(cost * count);
                System.out.print("Do you wish to continue selecting products? Y/N: ");
                continueChoice = reader.readLine();
            } while (continueChoice.equalsIgnoreCase("Y"));

            // 2. Select strategy (payment method)
            if (strategy == null) {
                System.out.println("Please, select a payment method:" + "\n" +
                        "1 - PalPay" + "\n" +
                        "2 - Credit Card");
                String paymentMethod = reader.readLine();

                // The client creates the concrete strategy.
                if (paymentMethod.equals("1")) {
                    strategy = new PayByPayPal();
                } else {
                    strategy = new PayByCreditCard();
                }
            }

            // 3. Process order (collect payment details using the selected strategy)
            order.processOrder(strategy);

            System.out.print("Pay " + order.getTotalCost() + " units or Continue shopping? P/C: ");
            String proceed = reader.readLine();
            
            if (proceed.equalsIgnoreCase("P")) {
                // 4. Execute the strategy (process payment)
                if (strategy.pay(order.getTotalCost())) {
                    System.out.println("Payment has been successful.");
                } else {
                    System.out.println("FAIL! Please, check your data.");
                }
                order.setClosed();
            }
        }
    }
}