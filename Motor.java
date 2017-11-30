import TI.*;
public class Motor
{
    private final Servo leftEngine, rightEngine;
    
    public Motor()
    {
        leftEngine = new Servo(12);
        rightEngine = new Servo(13);
    }
    /*
     * @param speedLeft snelheid in percentages linker wiel
     * @param speedRight snelheid in percentages rechter wiel
     */
    public void forward(int speedLeft, int speedRight)
    {
        speedLeft *= 2;
        speedRight *= 2;
        leftEngine.update(1500 - speedLeft);
        rightEngine.update(1500 + speedRight);
    }
    /*
     * @param speedLeft snelheid in percentages linker wiel
     * @param speedRight snelheid in percentages rechter wiel
     */
    public void backward(int speedLeft, int speedRight)
    {
        speedLeft *= 2;
        speedRight *= 2;
        leftEngine.update(1500 +speedLeft);
        rightEngine.update(1500 - speedRight);
    }
}
