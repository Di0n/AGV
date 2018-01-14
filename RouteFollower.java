import TI.*;
import java.util.*;
import java.awt.Point;
public class RouteFollower
{
    private final Transmission transmission;
    private final Detection detection;
    private final Queue<Route.ControlCode> controlCodes;
    private Route route;
    private int totalControlCodes;
    private int sensorLeft;
    private int sensorOuterLeft;
    private int sensorCenter;
    private int sensorRight;
    private final int sensorLeftPin = 2;
    private final int sensorOuterLeftPin = 3;
    private final int sensorRightPin = 0;
    private final int sensorCenterPin = 1;
    private boolean cancel;
    private int crossCounter;

    public RouteFollower(Transmission transmission, Detection detection)
    {
        this.transmission = transmission;
        this.detection = detection;
        controlCodes = new LinkedList<>();
    }

    public void setRoute(Route route)
    {
        controlCodes.clear();
        this.route = route;
        controlCodes.addAll(route.getControlCodes());
        totalControlCodes = controlCodes.size() + 1;
        crossCounter = 0;
        isPreviousCrossing = false;
        previousCenterCrossing = false;
        turning= false;
        cancel = false;
    }
    boolean isPreviousCrossing;
    boolean previousCenterCrossing;
    boolean turning; // in transmission
    public void update()
    {
        if(cancel == true) return;
        
        sensorLeft = BoeBot.analogRead(sensorLeftPin);
        sensorOuterLeft = BoeBot.analogRead(sensorOuterLeftPin);
        sensorCenter = BoeBot.analogRead(sensorCenterPin);
        sensorRight = BoeBot.analogRead(sensorRightPin);
        
        
        boolean outerCrossed = (isPreviousCrossing == true && detection.isOnCrossing() == false);
        boolean centerCrossed = (previousCenterCrossing == true && detection.isCenterOnLine() == false); // ?? detection
        
       
       if (turning == true && (centerCrossed || crossCounter >=1))
        {
            
            System.out.println("Counter: "+crossCounter);
            crossCounter++;
            if (detection.isCenterOnLine())
            {
                System.out.println("On line");
                
                transmission.goToSpeed(30);
                turning = false;

            }
            
        }
       if ( (turning == false && outerCrossed) || (controlCodes.size() == totalControlCodes - 1))
        {
            final Route.ControlCode nextStep = controlCodes.remove();
            switch (nextStep)
            {
                case FORWARD:
                // blijf vooruit gaan
                transmission.goToSpeed(30);
                System.out.println("Forward!");
                break;
                case LEFT:
                // maak een bocht naar links
                //transmission.turnDegrees(-90,100);
                transmission.turnLeft(30);
                turning = true;
                crossCounter = 0;
                System.out.println("left turn");
              
                break;
                case RIGHT:
                // maak een bocht naar rechts
                transmission.turnRight(30);
                turning = true;
                System.out.println("Right");
                crossCounter = 0;
               
                break;
                case STOP:
                // Stop, einde van route
                transmission.emergencyBrake();
                System.out.println("STOP");
                cancelRoute();
                return;
                default:
                return;
            }
        }
        
        if (turning == false)
       {
        
           if (sensorCenter > 900)
           {
               transmission.goToSpeed(30);
           }
           else if (sensorRight > 900) // wit 100 ~ 200
           {
               transmission.steerRight();
           }
           else if (sensorLeft > 900) // wit = 100
           {
              transmission.steerLeft(); 
           }
           else
           {
               transmission.goToSpeed(30);
           }
           
       }
        previousCenterCrossing = detection.isCenterOnLine();
        isPreviousCrossing = detection.isOnCrossing();
    }

    public int getCurrentStep()
    {
        return totalControlCodes - controlCodes.size();
    }

    public boolean hasRoute()
    {
        return controlCodes.size() > 0;
    }

    public void cancelRoute()
    {
        controlCodes.clear();
        cancel = true;
    }
    // @debug
    public int getOuterData()
    {
        return sensorOuterLeft;
    }
    
    public String getCurrentStepBTString() 
    { 
        return 'c'+Integer.toString(totalControlCodes - controlCodes.size()); 
    } 
    
}
