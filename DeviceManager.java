import java.util.ArrayList;
import java.util.List;

public class DeviceManager {
        private static int deviceID = 1;
        static List<Device> devices = new ArrayList<>();
        public static void listDevices(){
            if(devices.isEmpty()){
                System.out.println("room is not yet added");
            }
            else{
                for(int i=0; i<devices.size(); i++){
                    System.out.println(devices.get(i).getDeviceID());
                    System.out.println(devices.get(i).getDeviceName());
                    System.out.println(devices.get(i).getDeviceType());
                }
            }
        }
        public static void addDevices(String deviceName, String deviceType){
        devices.add(new LightDevice(deviceID, deviceName, deviceType));
        System.out.println("room added successfully");
        deviceID++;
        }
        public static void getDevice(int deviceID){
        //To Do: check using stream instead of looping
        for(int i=0; i<devices.size(); i++){
            if(devices.get(i).getDeviceID() == deviceID){
                System.out.println(devices.get(i).getDeviceID());
                System.out.println(devices.get(i).getDeviceName());
                System.out.println(devices.get(i).getDeviceType());                
            }
            else{
                System.out.println("The room with this room ID not exists.");
                break;
            }
        }
    }

}
