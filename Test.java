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
        Servo s1 = new Servo(12);
        Servo s2 = new Servo(13);

        int sensorLeft;
        int sensorRight;
        int sensorCenter;

        while(true)
        {
            sensorLeft = BoeBot.analogRead(1);
            sensorRight= BoeBot.analogRead(2);
            sensorCenter = BoeBot.analogRead(0);
            
            System.out.println("Rechts: "+ sensorRight);
            System.out.println("Midden: "+ sensorCenter);
            System.out.println("Links: "+ sensorLeft);
            
            if(sensorCenter > 900)
            {
                s1.update(1450);
                s2.update(1550);
            }
            else if(sensorRight > 900)
            {
                s1.update(1500);
                s2.update(1550);
            } 
            else if(sensorLeft > 900)
            {
                s1.update(1450);
                s2.update(1500);
            }
            else
            {
                s1.update(1500);
                s2.update(1500);
            }

            BoeBot.wait(10);
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
    

}
