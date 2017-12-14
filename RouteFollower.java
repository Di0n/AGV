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
    private boolean correction = false;
    private boolean stopTurn = false;
    private int crossCounter;
    //private TI.Timer t1;

    public RouteFollower(Transmission transmission, Detection detection)
    {
        this.transmission = transmission;
        this.detection = detection;
        controlCodes = new LinkedList<>();
        crossCounter = 0;
    }

    public void setRoute(Route route)
    {
        this.route = route;
        controlCodes.addAll(route.getControlCodes());
        totalControlCodes = controlCodes.size() + 1;
    }
    boolean isPreviousCrossing = false;
    boolean previousCenterCrossing = false;
    boolean turning; // in transmission
    boolean stopTurning = false;
     
    public void update()
    {
        if(cancel == true) return;
        sensorLeft = BoeBot.analogRead(sensorLeftPin);
        sensorOuterLeft = BoeBot.analogRead(sensorOuterLeftPin);
        sensorCenter = BoeBot.analogRead(sensorCenterPin);
        sensorRight = BoeBot.analogRead(sensorRightPin);
        //System.out.println("Left " + sensorLeft);
       // System.out.println("OuterLeft " + sensorOuterLeft);
        //System.out.println("Center " + sensorCenter);
        //System.out.println("Right " + sensorRight);
        //System.out.println("Overcorssing: " + (isPreviousCrossing == true && detection.isOnCrossing() == false));
        
        
        boolean outerCrossed = (isPreviousCrossing == true && detection.isOnCrossing() == false);
        boolean centerCrossed = (previousCenterCrossing == true && detection.isCenterOnLine() == false); // ?? detection
       /* if (turning == true && (centerCrossed || crossCounter >= 1))
        {
            System.out.println("Counter: "+crossCounter);
            crossCounter++;
            if (detection.isCenterOnLine())
            {
                System.out.println("On line");
                
                transmission.goToSpeed(30);
                turning = false;
            }
            
        }*/
        if (outerCrossed || controlCodes.size() == totalControlCodes - 1)
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
                transmission.turnRight(25);
                System.out.println("Right");
                break;
                case STOP:
                // Stop, einde van route
                transmission.emergencyBrake();
                System.out.println("STOP");
                break;
                default:
                return;
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
        cancel = true;
    }
    
}
