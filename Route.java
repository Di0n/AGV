import java.util.*;
import java.awt.Point;
public class Route
{
    public enum ControlCode
    {
        FORWARD,
        LEFT,
        RIGHT,
        STOP;
    }

    private ArrayList<ControlCode> controlCodes;
    //private ArrayList<Point> positions;
    
    /*public Route(ArrayList<Point> positions)
    {
        this.positions = positions;
    }*/
    public Route(ArrayList<ControlCode> controlCodes)
    {
        this.controlCodes = controlCodes;
    }
    public ArrayList<ControlCode> getControlCodes()
    {
        return controlCodes;
    }
    /*public ArrayList<Point> getPositions()
    {
        return positions;
    }*/

    /* Route protocol: >r(6,y),(x,y),(x,y)/0 
     * 
     *
    public static Route getRoute(ArrayList<Integer> data)
    {
        if ((!(data.size() > 0)) || data.get(0) != 0x3e) return null;
        
        Point startPos;
        ArrayList<Point> positions = new ArrayList<>();
        boolean isPos = false;
        for (int i = 1; i < data.size(); i++)
        {
            int current = data.get(i);
            if (current == 0x00) break;
            if (current == 0x72) continue;
            
            if (data.get(i) == 0x28)
            {
                int x = data.get((i += 1));
                int y = data.get((i += 2));
                positions.add(new Point(x, y));
            }
        }
        
        return new Route(positions);
    }*/
    
    /*
     * Route protocol voorbeeld: >,0x66,0x66, 0x6c, 0x66, 0x72, 0x66, 0x00
     * Vertaald: 0x3e > geeft begin aan van reeks, als dit ontbreekt is het geen geldig verzoek
     * 0x66 FORWARD Voorwaards
     * 0x6c LEFT Links
     * 0x72 RIGHT Rechts
     * 0x73 STOP Stop
     * 0x00 \0 Null terminator geeft eind van reeks aan
     * @param data protocol
     * @return Route object
     */
    public static Route getRoute(ArrayList<Integer> data)
    {
        if(!(data.size() > 0)) return null;    
        else if(data.get(0) != 0x3e) return null;  // > char

        ArrayList<ControlCode> controlCodes = new ArrayList<>();
        for(int i = 1 ; i < data.size() ; i++)
        {
            if(data.get(i) == 0x00)  // \0 char
            {
                break;
            }
            switch(data.get(i))
            {
                case 0x66:
                    controlCodes.add(ControlCode.FORWARD);
                break;
                case 0x6c:
                    controlCodes.add(ControlCode.LEFT);
                break;
                case 0x72:
                    controlCodes.add(ControlCode.RIGHT);
                break;
                case 0x73:
                    controlCodes.add(ControlCode.STOP);
                break;
                default:
                    return null;
            }
        }
        return new Route(controlCodes);
    }
}