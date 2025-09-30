package order;


import strategies.PayStrategy;


 //Context class: It holds a reference to the concrete strategy object and delegates the work.
public class Order {
    private int totalCost = 0;
    private boolean isClosed = false;

    // Order delegates the collection of payment details to the strategy
    public void processOrder(PayStrategy strategy) {
        strategy.collectPaymentDetails();
    }

    public void setTotalCost(int cost) {
        this.totalCost += cost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed() {
        isClosed = true;
    }
}