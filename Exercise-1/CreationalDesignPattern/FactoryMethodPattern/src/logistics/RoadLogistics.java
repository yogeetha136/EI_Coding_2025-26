// Concrete Creator
package logistics;

import InterfaceVehicle.Vehicle;
import vehicles.Truck;


public class RoadLogistics extends Logistics {

    @Override
    public Vehicle createVehicle() {
        // In a real app, this might choose a Car or Truck based on load/distance.
        // For simplicity, we just return a Truck here.
        return new Truck(); 
    }
}
