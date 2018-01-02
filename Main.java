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
        boolean testBt = false;

        while (true)
        {
            BoeBot.wait(1);
            
            control.update();
            transmission.update();
            detection.update();
            
            //System.out.println("stop: "+detection.getDistanceToObject());
            if (detection.getDistanceToObject() <= 20 && transmission.currentSpeed() > 0 && !control.isRemoteControlEnabled())// 40 // 15 centimeter
            {
                /*
                System.out.println("stop: "+detection.getDistanceToObject());
                transmission.emergencyBrake();
                if (routeFollower.hasRoute()) routeFollower.cancelRoute();
                continue;*/
            }
            if (routeFollower.hasRoute())
            {
                routeFollower.update();
                //bluetooth.writeDirectly(routeFollower.getOuterData());
                //if (!bluetooth.isWriting())
                   // bluetooth.writeString(routeFollower.getCurrentStepBTString());
            }
                
             if(testBt == true)
            {
                //>,0x66,0x66, 0x6c, 0x66, 0x72, 0x66, 0x00
                ArrayList<Integer> ssss = new ArrayList<>();
                ssss.add(0x3e);
                ssss.add(0x66);
                ssss.add(0x6c); //0x66 Forward
                ssss.add(0x66);
                ssss.add(0x6c);
                ssss.add(0x66);
                ssss.add(0x73);
                ssss.add(0x00);
                
                Route route = Route.getRoute(ssss); 
                
                if (route != null)
                {    
                    routeFollower.setRoute(route);
                    System.out.println("Route accepted!");
                    ArrayList<Route.ControlCode> codes = new ArrayList<>();
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
                    bluetooth.sendAcknowledge();
                    ArrayList<Route.ControlCode> codes = new ArrayList<>();
                    for (Route.ControlCode c : codes)
                    {
                        System.out.println(c.toString());
                    }
                } 
            }
            
            
        }
    }
}
