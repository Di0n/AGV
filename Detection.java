import TI.*;
public class Detection
{
    private int pulseOutPin;        // 10
    private int pulseInPin;         // 11
    private int pulseLength;        // 200000 = 5 meter
    private int distanceToObject;   // In centimeters
    private int sensorLeft;
    private int sensorRight;
    private int sensorCenter;
    private Transmission transmission;
    
    public Detection(int pulseOutPin, int pulseInPin, int pulseLength)
    {
        this.pulseOutPin = pulseOutPin;
        this.pulseInPin = pulseInPin;
        this.pulseLength = pulseLength;
    }
    
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
    }
    
    /*
     * Geeft de afstand in centimeters tot het object voor de Ultrasoon sensor.
     * @return Afstand in centimeters.
     */
    public int getDistanceToObject()
    {
        return distanceToObject;
    }
    
   
}
