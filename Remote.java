import TI.*;
public class Remote
{
    private final int irPin = 15;
    private final String deviceID = "00011";
    private RemoteButton buttonPressed;
    
    public enum RemoteButton
    {
        NONE(-1),
        FORWARD(4),
        FWD_RIGHT(5),
        RIGHT(8),
        BWD_RIGHT(37),
        BACKWARD(9),
        BWD_LEFT(12),
        LEFT(6),
        FWD_LEFT(3),
        STOP(7),
        EMERGENCY_BRAKE(21),
        ENABLE_REMOTE_CONTROL(77),
        DISABLE_REMOTE_CONTROL(78);
        
        private final int value;
        private RemoteButton(int value) 
        {
            this.value = value;
        }
        
        public int getValue()
        {
            return value;
        }
        
        public static RemoteButton getButton(int value)
        {
            for (RemoteButton r : RemoteButton.values())
            {
                if (r.getValue() == value)
                    return r;
            }
            return RemoteButton.NONE;
        }
    }
    

    public Remote()
    {
        buttonPressed = RemoteButton.NONE;
    }
    
    public void update()
    {
        buttonPressed = RemoteButton.NONE;
        int pulseLength = BoeBot.pulseIn(irPin, false, 6000);
        
        if (pulseLength > 2000)
        {
            int lengths[] = new int[12];
            
            for (int i = 0; i < 12; i++)
                lengths[i] = BoeBot.pulseIn(irPin, false, 20000) < 1000 ? 0 : 1;
            
            if (lengths.length != 12) return; // ongeldig signaal
            DecodedIRSignal decodedIRSignal = decode(lengths);
            
            //if (decodedIRSignal.getDeviceID() == deviceID) // Beantwoord alleen de gekozen deviceID
                buttonPressed = decodedIRSignal.getRemoteButton();
                //System.out.println("Button Value: "+buttonPressed + "\nDeviceID: "+decodedIRSignal.getDeviceID());
        }
    }
    
    /*
     * Decode het IR signaal.
     * @param lengths Puls lengte.
     * @return Geeft een decoded IRSignaal state object terug.
     */
    private DecodedIRSignal decode(int[] lengths)
    {
        StringBuilder deviceID = new StringBuilder();
        StringBuilder val = new StringBuilder();
        
        for (int i = 11; i >= 0; i--)
        {
            if (i >= 7)
                deviceID.append(lengths[i]);
            else
                val.append(lengths[i]);
        }

        int control = Integer.parseInt(val.toString(), 2);
        return new DecodedIRSignal(deviceID.toString(), RemoteButton.getButton(control));
    }
    
    public RemoteButton getKeyPressed()
    {
        return buttonPressed;
    }
    
    private class DecodedIRSignal
    {
        private String deviceID;
        private RemoteButton button;
        
        public DecodedIRSignal(String deviceID, RemoteButton button)
        {
            this.deviceID = deviceID;
            this.button = button;
        }
        
        public String getDeviceID()
        {
            return deviceID;
        }
        
        public RemoteButton getRemoteButton()
        {
            return button;
        }
    }
    
}


