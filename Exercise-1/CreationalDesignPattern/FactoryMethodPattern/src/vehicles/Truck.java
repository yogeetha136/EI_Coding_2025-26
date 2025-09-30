// Concrete Product 2
package vehicles;
import InterfaceVehicle.Vehicle;

public class Truck implements Vehicle {
    @Override
    public void deliver() {
        System.out.println("\nDelivering large cargo by Truck on the road.");
    }
}