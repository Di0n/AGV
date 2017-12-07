import TI.*;
/**
 * class test - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Test
{
    public static void main(String[] args)
    {
        Servo s1 = new Servo(13);
        Servo s2 = new Servo(12);

        int sensorLeft;
        int sensorRight;
        int sensorCenter;
        int sensorOuterLeft;;;;;;;;;;;;;
        boolean kruising = false;
        int kruispunt = 0;
        while(true)
        {
            sensorLeft = BoeBot.analogRead(2);
            sensorRight= BoeBot.analogRead(1);
            sensorCenter = BoeBot.analogRead(0);
            sensorOuterLeft = BoeBot.analogRead(3);
            //System.out.println("Rechts: "+ sensorRight);
            //System.out.println("Midden: "+ sensorCenter);
            //System.out.println("Links: "+ sensorLeft);
            System.out.println("OuterLeft" + sensorOuterLeft);
            if(sensorOuterLeft > 700)
            {
                kruising = true;
            }
            else if(sensorOuterLeft < 1200 && kruising == true)
            {
                kruispunt ++;
                kruising = false;
                System.out.println("kruispunt" + kruispunt + "\n");
            }
            else if(sensorCenter > 500)
            {
                kruising = false;
                s1.update(1450);
                s2.update(1550);
            }
            else if(sensorRight > 500)
            {
                kruising = false;
                s1.update(1500);
                s2.update(1530);
            } 
            else if(sensorLeft > 500)
            {
                kruising = false;
                s1.update(1470);
                s2.update(1500);
            }
            else
            {
                kruising = false;
                s1.update(1500);
                s2.update(1500);
            }
            
            
            BoeBot.wait(100);
        }

    }

    public void FollowLine()
    {
        Servo s1 = new Servo(12);
        Servo s2 = new Servo(13);

        int sensorLeft;
        int sensorRight;
        int sensorCenter;    

        //sensorLeft = BoeBot.analogRead(1);
        //sensorRight= BoeBot.analogRead(0);
        //sensorCenter = BoeBot.analogRead(2);
        /*
        System.out.println("Rechts: "+ sensorRight);
        System.out.println("Midden: "+ sensorCenter);
        System.out.println("Links: "+ sensorLeft);

        if(sensorCenter > 900)
        {
        s1.update(1450);
        s2.update(1550);
        }
        else if(sensorRight < 900)
        {
        s1.update(1450);
        s2.update(1530);
        } 
        else if(sensorLeft < 900)
        {
        s1.update(1550);
        s2.update(1450);
        }
         */
    }

    /*
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

     */

}
