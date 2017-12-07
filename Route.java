import java.util.*;
public class Route
{
    public enum ControlCode
    {
        FORWARD,
        LEFT,
        RIGHT;
        //STOP;
    }

    private ArrayList<ControlCode> controlCodes;
    public Route(ArrayList<ControlCode> controlCodes)
    {
        this.controlCodes = controlCodes;
    }

    public ArrayList<ControlCode> getControlCodes()
    {
        return controlCodes;
    }

    public static Route getRoute(int[] data)
    {
        if(!(data.length > 0)) return null;    
        else if(data[0] != 0x3e) return null;  // > char

        ArrayList<ControlCode> controlCodes = new ArrayList<>();
        for(int i = 1 ; i < data.length ; i++)
        {
            if(data[i] == 0x00)  // \0 char
            {
                break;
            }
            switch(data[i])
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
                //case 0x73:
                //controlCodes.add(ControlCode.STOP);
                //break;
                default:
                return null;
            }
        }
        return new Route(controlCodes);
    }
}

