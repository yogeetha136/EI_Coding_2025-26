import java.util.*;

abstract class Device implements DeviceInterface{
    abstract void createDevice();

    public boolean getPing(){
        return true;
    }

    
}
