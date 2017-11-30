import TI.*;

public class Led
{
    private Timer t1;
    private Timer t2;
    private int led1; //0
    private int led2; //1
    private int led3; //2
    private int led4; //3
    private int led5; //4
    private int led6; //5
    
    public Led()
    {
        led1 = 0;
        led2 = 1;
        led3 = 2;
        led4 = 3;
        led5 = 4;
        led6 = 5;
        t1 = new Timer(250);
        t2 = new Timer(1000);
        t1.mark();
        t2.mark();
    }
    
    public void update()
    {
            if(t1.timeout())
            {
                BoeBot.rgbSet(led1,255,102,0); // oranje
                BoeBot.rgbSet(led6,255,102,0);
                BoeBot.rgbShow();
            }
            if(t2.timeout())
            {
               BoeBot.rgbSet(led1,0,0,0); // uit led 2
               BoeBot.rgbSet(led6,0,0,0); // uit led 5
               BoeBot.rgbShow();
            }
    }
    
    // 2 led's laten knipperen. 
    public  void ledBlinkFront()
    {

    }
}
