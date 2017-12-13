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
        boolean gek = false;
        boolean testBt = true;
        while (true)
        {
            transmission.update();
            detection.update();

            if (detection.getDistanceToObject() <= 45 && transmission.currentSpeed() > 0 && !control.isRemoteControlEnabled()) // 15 centimeter
            {
                transmission.goSpeedToSlow(-100);
                //transmission.emergencyBrake();
            }
            if (routeFollower.hasRoute())
                routeFollower.update();
            if (testBt == true)
            {
                //>,0x66,0x66, 0x6c, 0x66, 0x72, 0x66, 0x00
                ArrayList<Integer> ssss = new ArrayList<>();
                ssss.add(0x3e);
                ssss.add(0x66);
                ssss.add(0x66);
                ssss.add(0x66);
                ssss.add(0x00);
                Route route = Route.getRoute(ssss); 
                if (route != null)
                {    
                    routeFollower.setRoute(route);
                    System.out.println("Route accepted!");
                    ArrayList<Route.ControlCode> codes = new ArrayList<>();
                    for (Route.ControlCode c : codes)
                    {
                        System.out.println(c.toString());
                    }

                } 
                testBt = false;
            }
            bluetooth.update();
            if (bluetooth.dataReady())
            {
                System.out.println("ontvangen");
                Route route = Route.getRoute(bluetooth.getData()); 
                if (route != null)
                {    
                    routeFollower.setRoute(route);
                    System.out.println("Route accepted!");
                    ArrayList<Route.ControlCode> codes = new ArrayList<>();
                    for (Route.ControlCode c : codes)
                    {
                        System.out.println(c.toString());
                    }

                } 

            }

            BoeBot.wait(1);
        }
    }
}
