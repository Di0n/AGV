import TI.*;
import java.awt.Color;

public class Led
{
    Timer blinkTimer;
    private final int led;
    private Color ledColor;
    private int blinkTime;
    public Led(int led)
    {
        this.led = led;
        this.ledColor = Color.BLACK;
    }
    
    public void update()
    {
        if (blinkTimer != null && blinkTimer.timeout())
        {
            // check of blinktime niet voorbij is.
            // TODO Switchen tussen rgbSet(led, color); en zwart 
        }
    }
    public void blink(int milliseconds, Color color)
    {
        blinkTimer = new Timer(1250);
        blinkTimer.mark();
        BoeBot.rgbSet(led, color);
        blinkTime = milliseconds;
    }
    public void turnOn()
    {
    }
    
    public void turnOff()
    {
    }
    
}
