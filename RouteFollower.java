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

    public RouteFollower(Transmission transmission, Detection detection)
    {
        this.transmission = transmission;
        this.detection = detection;
        controlCodes = new LinkedList<>();
    }

    public void setRoute(Route route)
    {
        this.route = route;
        controlCodes.addAll(route.getControlCodes());
        totalControlCodes = controlCodes.size() + 1;
    }
    boolean isPreviousCrossing = false;
    public void update()
    {
        sensorLeft = BoeBot.analogRead(sensorLeftPin);
        sensorOuterLeft = BoeBot.analogRead(sensorOuterLeftPin);
        sensorCenter = BoeBot.analogRead(sensorCenterPin);
        sensorRight = BoeBot.analogRead(sensorRightPin);
        System.out.println("Left " + sensorLeft);
        System.out.println("OuterLeft " + sensorOuterLeft);
        System.out.println("Center " + sensorCenter);
        System.out.println("Right " + sensorRight);
        
            if(sensorCenter > 500)
            {
                transmission.goToSpeed(25);
                
            }
            else if(sensorRight > 500)
            {
                transmission.steerRight();
                System.out.println("test");
            } 
            else if(sensorLeft > 500)
            {
                transmission.steerLeft();
                                System.out.println("test");
            }
        

        if ((isPreviousCrossing == true && detection.isOnCrossing() == false)|| controlCodes.size() == totalControlCodes - 1)
        {
            final Route.ControlCode nextStep = controlCodes.remove();
            switch (nextStep)
            {
                case FORWARD:
                // blijf vooruit gaan
                transmission.goToSpeed(25);
                break;
                case LEFT:
                // maak een bocht naar links
                transmission.turnLeft(30);
                System.out.println("left");

                break;
                case RIGHT:
                // maak een bocht naar rechts
                transmission.turnRight();
                break;
                case STOP:
                // Stop, einde van route
                transmission.emergencyBrake();
                break;
                default:
                return;
            }

        }
        isPreviousCrossing = detection.isOnCrossing();
    }

    public int getCurrentStep()
    {
        return totalControlCodes - controlCodes.size();
    }
    
    public String getCurrentStepBTString()
    {
        return 'c'+Integer.toString(totalControlCodes - controlCodes.size());
    }

    public boolean hasRoute()
    {
        return controlCodes.size() > 0;
    }
    /*private enum Heading
    {
    NORTH,
    SOUTH,
    WEST,
    EAST;
    }
    private enum Move
    {
    FORWARD,
    LEFT,
    RIGHT,
    STOP;
    }
    private final Transmission transmission;
    private final Detection detection;
    private Route route;
    private int currentPosIndex;
    private Queue<Point> positions;
    // private Point currentPosition;
    private Heading currentHeading;

    public RouteFollower(Transmission transmission, Detection detection)
    {
    this.transmission = transmission;
    this.detection = detection;
    positions = new LinkedList<>();
    }

    public void setRoute(Route route)
    {
    this.route = route;
    positions.addAll(route.getPositions());
    currentPosIndex = 0;
    Point targetPos = route.getPositions().get(1);
    Point currentPos = positions.peek();
    Point direction = new Point(targetPos.x - currentPos.x, targetPos.y - currentPos.y);
    if (direction.x == 1)
    currentHeading = Heading.EAST;
    else if (direction.x == -1)
    currentHeading = Heading.WEST;
    else if (direction.y == 1)
    currentHeading = Heading.SOUTH;
    else if (direction.y == -1)
    currentHeading = Heading.NORTH;
    }

    public void update()
    {
    if (detection.isOnCrossing())
    {
    Point currentPosition = positions.remove();
    Point target = positions.peek();

    //Point direction = new Point(target.x - currentPosition.x, target.y - currentPosition.y);
    Move dir = getDirection(target, currentPosition, currentHeading);
    if (currentHeading == Heading.NORTH && dir == Move.LEFT)
    {

    currentHeading = Heading.WEST;
    System.out.println("Going left");
    }
    else if (currentHeading == Heading.NORTH && dir == Move.RIGHT)
    { 
    currentHeading = Heading.EAST;
    System.out.println("Going right");
    }
    else if (currentHeading == Heading.EAST && dir == Move.LEFT)
    {
    currentHeading = Heading.NORTH;
    System.out.println("Going left");
    }
    else if (currentHeading == Heading.EAST && dir == Move.RIGHT)
    { 
    currentHeading = Heading.SOUTH;
    System.out.println("Going right");
    }
    else if (currentHeading == Heading.SOUTH && dir == Move.LEFT)
    {
    currentHeading = Heading.EAST;
    System.out.println("Going left");
    }
    else if (currentHeading == Heading.SOUTH && dir == Move.RIGHT)
    {    
    currentHeading = Heading.WEST;
    System.out.println("Going right");
    }   
    else if (currentHeading == Heading.WEST && dir == Move.LEFT)
    {
    currentHeading = Heading.SOUTH;
    System.out.println("Going left");
    }
    else if (currentHeading == Heading.WEST && dir == Move.RIGHT)
    {
    currentHeading = Heading.NORTH;
    System.out.println("Going right");
    }
    }
    else
    {
    transmission.goSlowToSpeed(40);
    System.out.println("Not on crossing");
    }
    }

    private Move getDirection(Point targetPos, Point currentPosition, Heading heading)
    {
    Point direction = new Point(targetPos.x - currentPosition.x, targetPos.y - currentPosition.y);
    if (direction.x == 0 && direction.y == 0)
    return Move.STOP;
    switch (heading)
    {
    case NORTH:
    if (direction.y == -1)
    return Move.FORWARD;
    else if (direction.x == -1)
    return Move.LEFT;
    else if (direction.x == 1)
    return Move.RIGHT;
    break;
    case SOUTH:
    if (direction.y == 1)
    return Move.FORWARD;
    else if (direction.x == -1)
    return Move.RIGHT;
    else if (direction.x == 1)
    return Move.LEFT;
    break;
    case WEST:
    if (direction.x == -1)
    return Move.FORWARD;
    else if (direction.y == 1)
    return Move.LEFT;
    else if (direction.y == -1)
    return Move.RIGHT;
    break;
    case EAST:
    if (direction.x == 1)
    return Move.FORWARD;
    else if (direction.y == -1)
    return Move.LEFT;
    else if (direction.y == 1)
    return Move.RIGHT;
    break;
    }
    return null; // invalid
    }
    public boolean hasRoute()
    {
    return positions.size() > 0;
    }*/
}
