import java.util.*;
public class Notification
{
    private Speaker speaker;
    private Led led;
    private ArrayList<Led> leds;
    
    public Notification()
    {
        leds = new ArrayList<>();
    }
    
    public void update()
    {
        for(Led led : leds)
        {
            led.update();
        }
        speaker.update();
    }
    
    public void blink(int led)
    {
        leds.add(new Led(led));
    }
}