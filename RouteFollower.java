import TI.*;
import java.util.*;
import java.awt.Point;
public class RouteFollower
{
    private final Transmission transmission;
    private final Detection detection;
    private Route route;
    private int currentPosIndex;
    private Queue<Point> positions;
    private Point currentPosition;
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
    }
    
    public void update()
    {
        if (detection.isOnCrossing())
        {
            currentPosition = positions.remove();
            Point target = positions.peek();
            
            Point direction = new Point(target.x - currentPosition.x, target.y - currentPosition.y);
            
        }
        else
        {
            transmission.goSlowToSpeed(40);
        }
    }
    public boolean hasRoute()
    {
        return positions.size() > 0;
    }
}
