import TI.*;
public class Detection
{
    private int pulseOutPin;        // 10
    private int pulseInPin;         // 11
    private int pulseLength;        // 200000 = 5 meter
    private int distanceToObject;   // In centimeters
    private final int sensorLeftPin = 0;
    private final int sensorOuterLeftPin = 3;
    private final int sensorRightPin = 2;
    private final int sensorCenterPin = 1;
    private int sensorLeft;
    private int sensorOuterLeft;
    private int sensorCenter;
    private int sensorRight;
    
    
    public Detection(int pulseOutPin, int pulseInPin, int pulseLength)
    {
        this.pulseOutPin = pulseOutPin;
        this.pulseInPin = pulseInPin;
        this.pulseLength = pulseLength;
    }
    private boolean crossing = false;
    /*
     * Update de Ultrasoon sensor.
     */
    public void update()
    {
        BoeBot.digitalWrite(pulseOutPin, true);
        BoeBot.wait(1);
        BoeBot.digitalWrite(pulseOutPin, false);
        
        int pulse = BoeBot.pulseIn(pulseInPin, true, pulseLength);
        distanceToObject = pulse / 58; // afstand in centimeters
        //System.out.println(distanceToObject);
        
        sensorLeft = BoeBot.analogRead(sensorLeftPin);
        sensorOuterLeft = BoeBot.analogRead(sensorOuterLeftPin);
        sensorCenter = BoeBot.analogRead(sensorCenterPin);
        sensorRight = BoeBot.analogRead(sensorRightPin);
        
        if (sensorOuterLeft > 700) crossing = true;
        
        if (sensorOuterLeft < 1200 && crossing) crossing = false;
    }
    
    /*
     * Geeft de afstand in centimeters tot het object voor de Ultrasoon sensor.
     * @return Afstand in centimeters.
     */
    public int getDistanceToObject()
    {
        return distanceToObject;
    }
    
    public boolean isOnCrossing()
    {
        return crossing;
    }
   
}
