import TI.*;
import java.awt.Color;

public class Led
{
    Timer blinkTimer1;
    Timer blinkTimer2;
    private final int led;
    private Color ledColor;
    private int blinkTime;
    private boolean on;
    
    public Led(int led)
    {
        this.led = led;
        this.ledColor = Color.BLACK;
        on = false;
    }
    
    public void update()
    {
        if (blinkTimer1 != null && blinkTimer1.timeout() && on == true)
        {
            // check of blinktime niet voorbij is.
            // TODO Switchen tussen rgbSet(led, color); en zwart 
            BoeBot.rgbSet(led, ledColor); // oranje
            BoeBot.rgbShow();
        }
        
        if(blinkTimer2 != null && blinkTimer2.timeout() && on == true)
        {
               BoeBot.rgbSet(led,0,0,0); // uit led 2
               BoeBot.rgbShow();
        }
    }
    
    /*
    public void blink(Color color, int milliseconds)
    {
        blinkTimer = new Timer(1250);
        blinkTimer.mark();
        BoeBot.rgbSet(led, color);
        blinkTime = milliseconds;
    }
    */
   
    public void blink(Color color)
    {
        blinkTimer1 = new Timer(250);
        blinkTimer2 = new Timer(1000);
        blinkTimer1.mark();
        blinkTimer2.mark();
        on = true;
        ledColor = color;
    }
    
    public void turnOnConstant(int led, Color color)
    {
        on = true;
        ledColor = color;
        BoeBot.rgbSet(led, ledColor);
    }
    
    public void turnOff(int led)
    {
        on = false;
        BoeBot.rgbSet(led,0,0,0);
        BoeBot.rgbShow();
    }
}
