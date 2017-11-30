import TI.*;
public class Motor
{
    private final Servo leftEngine, rightEngine;
    private final int DEFAULT_ENGINE_SPEED = 1500;
    
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
    
    public void left(int speedLeft, int speedRight)
    {
        speedLeft *= 2;
        speedRight *= 2;
    }
    public void stopMotor()
    {
        leftEngine.update(DEFAULT_ENGINE_SPEED);
        rightEngine.update(DEFAULT_ENGINE_SPEED);
    }
}
