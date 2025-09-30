// Client Code
import logistics.Logistics;
import logistics.RoadLogistics;


public class Main {
    public static void main(String[] args) {
        // We work with the abstract creator (Logistics), but instantiate a concrete creator (RoadLogistics).
        Logistics logistics = new RoadLogistics();

        System.out.println("Starting delivery simulation:");
        logistics.planDelivery();
    }
}