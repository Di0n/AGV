import TI.*;
import java.util.*;
public class Main
{
    
    public static void main(String[] args)
    {
        Detection detection = new Detection(10, 11,10000);
        Transmission transmission = new Transmission(12, 13);
        RemoteControl control = new RemoteControl(transmission, true);
        System.out.println("Running...");
 
        while (true)
        {
            transmission.update();
            detection.update();
            //System.out.println("dist to obj: "+detection.getDistanceToObject());
            //System.out.println("Speed: "+transmission.currentSpeed());
            if (detection.getDistanceToObject() <= 45 && transmission.currentSpeed() > 0 && !control.isRemoteControlEnabled()) // 15 centimeter
            {
                transmission.goSpeedToSlow(-100);
                //transmission.emergencyBrake();
                System.out.println("Stopped");
            }
            control.update();
            BoeBot.wait(10);
        }
    }
}
