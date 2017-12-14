import TI.*;
import java.util.*;
import java.awt.Point;
public class Main
{
    // Main
    // test Ralph gitkraken
    public static void main(String[] args)
    {
        Detection detection = new Detection(10, 11,10000);
        Transmission transmission = new Transmission(12, 13);
        RemoteControl control = new RemoteControl(transmission, false);
        RouteFollower routeFollower = new RouteFollower(transmission, detection);
        Bluetooth bluetooth = new Bluetooth();
        System.out.println("Running...");
        boolean testBt = true;
        while (true)
        {
            control.update();
            transmission.update();
            detection.update();
            
            if (detection.getDistanceToObject() <= 45 && transmission.currentSpeed() > 0 && !control.isRemoteControlEnabled()) // 15 centimeter
            {
                transmission.goSpeedToSlow(-100);
                //transmission.emergencyBrake();
            }
            if (routeFollower.hasRoute())
            {
                routeFollower.update();
                if (!bluetooth.isWriting())
                    bluetooth.writeString(routeFollower.getCurrentStepBTString());
            }
                
            bluetooth.update();
            if (bluetooth.dataReady())
            {
                System.out.println("ontvangen");
                Route route = Route.getRoute(bluetooth.getData()); 
                if (route != null)
                {    
                    routeFollower.setRoute(route);
                    System.out.println("Route accepted!"); // todo route toString()
                    bluetooth.sendAcknowledge();
                }

            }

            BoeBot.wait(1);
        }
    }
}
