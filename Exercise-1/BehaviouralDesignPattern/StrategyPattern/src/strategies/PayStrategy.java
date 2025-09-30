package strategies;

// interface
public interface PayStrategy {
    boolean pay(int paymentAmount);
    void collectPaymentDetails();
}
