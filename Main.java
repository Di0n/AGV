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
        RemoteControl control = new RemoteControl(transmission, true);
        Bluetooth bluetooth = new Bluetooth();
        System.out.println("Running...");
 
        while (true)
        {
            transmission.update();
            detection.update();

            if (detection.getDistanceToObject() <= 45 && transmission.currentSpeed() > 0 && !control.isRemoteControlEnabled()) // 15 centimeter
            {
                transmission.goSpeedToSlow(-100);
                //transmission.emergencyBrake();
                System.out.println("Stopped");
            }
            control.update();
            
            bluetooth.update();
            if (bluetooth.dataReady())
            {
                Route route = Route.getRoute(bluetooth.getData()); 
                if (route != null)
                {    
                    System.out.println("Route accepted!");
                    ArrayList<Point> positions = route.getPositions();
                    for (int i = 0; i < positions.size(); i++)
                    {
                        Point current = positions.get(i);
                        System.out.println("X: "+ current.x + " Y: "+current.y);
                    }
                } 
                
            }
            BoeBot.wait(1);
        }
    }
}
