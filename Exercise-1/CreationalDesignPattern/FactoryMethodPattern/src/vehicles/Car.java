// Concrete Product 1
package vehicles;
import InterfaceVehicle.Vehicle;

public class Car implements Vehicle {
    @Override
    public void deliver() {
        System.out.println("\nDelivering small items by Car on the road.");
    }
}
