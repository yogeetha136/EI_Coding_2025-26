// Creator (Abstract Factory)
package logistics;

import InterfaceVehicle.Vehicle;


public abstract class Logistics {


    public void planDelivery() {
        // The creator calls the factory method to get a product instance.
        Vehicle vehicle = createVehicle();
        
        System.out.println("\nLogistics planned. Ready to go...");
        vehicle.deliver();
    }

 
    public abstract Vehicle createVehicle();
}
