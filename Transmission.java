import TI.*;
public class Transmission
{
    private Motor motor;
    private Timer turnTimer;
    private int targetSpeedLeft;
    private int targetSpeedRight;
    private boolean accelerate;
    private boolean stop;
    private int speedPercentage1;
    private Servo leftServo, rightServo;
    
    public Transmission(int leftServoPin, int rightServoPin)
    {
        //motor = new Motor();
        leftServo = new Servo(leftServoPin);
        rightServo = new Servo(rightServoPin);
        turnTimer = null;
        accelerate = false;
        stop = false;
    }

    public void update()
    {
        if (turnTimer != null && turnTimer.timeout())
        {
            leftServo.update(1500);
            rightServo.update(1500);
            turnTimer = null;
        }
        if(speedPercentage1 > 0)
        {
            if(rightServo.getPulseWidth() != targetSpeedRight && accelerate == true  && rightServo.getPulseWidth() <= 1700 )
            {
                int servoRightUpdate = rightServo.getPulseWidth();
                servoRightUpdate = servoRightUpdate + 1;
                rightServo.update(servoRightUpdate);
                //System.out.println(rightServo.getPulseWidth());
                //System.out.println(leftServo.getPulseWidth());            
            }

            if(leftServo.getPulseWidth() != targetSpeedLeft && accelerate == true)
            {
                int servoLeftUpdate = leftServo.getPulseWidth();
                servoLeftUpdate = servoLeftUpdate - 1;
                leftServo.update(servoLeftUpdate); 
            }
        }

        if(speedPercentage1 < 0)
        {
            if(rightServo.getPulseWidth() != targetSpeedRight && accelerate == true  && rightServo.getPulseWidth() <= 1700)
            {
                int servoRightUpdate = rightServo.getPulseWidth();
                servoRightUpdate = servoRightUpdate - 1;
                rightServo.update(servoRightUpdate);
                //System.out.println(rightServo.getPulseWidth());
                //System.out.println(leftServo.getPulseWidth());            
            }

            if(leftServo.getPulseWidth() != targetSpeedLeft && accelerate == true)
            {
                int servoLeftUpdate = leftServo.getPulseWidth();
                servoLeftUpdate = servoLeftUpdate + 1;
                leftServo.update(servoLeftUpdate); 
            }
        }

        if(speedPercentage1 < 0 && stop == true)
        {
            if(rightServo.getPulseWidth() != targetSpeedRight && rightServo.getPulseWidth() >= 1500)
            {
                int servoRightUpdate = rightServo.getPulseWidth();
                servoRightUpdate = servoRightUpdate - 1;
                rightServo.update(servoRightUpdate);
                //System.out.println(rightServo.getPulseWidth());
                //System.out.println(leftServo.getPulseWidth());            
            }

            if(leftServo.getPulseWidth() != targetSpeedLeft && leftServo.getPulseWidth() <= 1500)
            {
                int servoLeftUpdate = leftServo.getPulseWidth();
                servoLeftUpdate = servoLeftUpdate + 1;
                leftServo.update(servoLeftUpdate); 
            }
        }
           
        if((leftServo.getPulseWidth() == targetSpeedLeft) && (rightServo.getPulseWidth() != targetSpeedRight))
            {
                accelerate = false;
                stop = false;
            }


        }

        public void goToSpeed(int speedPercentage)
        {
        int maxSpeed = 200;
        int speedUnit = 2;
        int speed = speedPercentage * speedUnit;

        int speedLeft = 1500 - speed;
        int speedRight = 1500 + speed;

        leftServo.update(speedLeft);
        rightServo.update(speedRight);
    }

    public void turnRight()
    {
        leftServo.update(1700);
        rightServo.update(1700);
    }

    public void turnLeft(int percentage)
    {
        int maxSpeed = 200;
        int speedUnit = 2;
        int speed = percentage * speedUnit;

        int speedLeft = 1500 - speed;
        int speedRight = 1500 - speed;
        leftServo.update(speedLeft);
        rightServo.update(speedRight);
    }
    
    public void steerLeft()
    {
        leftServo.update(1300);
        rightServo.update(1500);
    }
    
    public void steerRight()
    {
        leftServo.update(1500);
        rightServo.update(1700);
    }
        

    /*
     * @param degrees Graden van 1 t/m 180 en -1 t/m -180
     * @param speedPercentage Snelheid in percentage van 1 t/m 100
     */
    public void turnDegrees(int degrees, int speedPercentage)
    {

        if (degrees < -180 || degrees > 180 || speedPercentage < 1 || speedPercentage > 100)
            return; 

        final int fullSpeed360Turn = 1200; // 1600, 1500, 1300
        final int percentage = fullSpeed360Turn / 100 * speedPercentage;

        final int newTurnSpeed360 = fullSpeed360Turn - percentage + fullSpeed360Turn;

        final int turnSpeed = newTurnSpeed360 / (360 / Math.abs(degrees));

        final int speed = speedPercentage * 2;    

        if (degrees < 0) // Naar links gaan
        {
            leftServo.update(1500 - speed);
            rightServo.update(1500 - speed);
        }
        else if (degrees > 0)  // Naar rechts gaan
        {
            leftServo.update(1500 + speed);
            rightServo.update(1500 + speed);
        }

        turnTimer = new Timer(turnSpeed);
        turnTimer.mark();
    }

    public void emergencyBrake()
    {
        leftServo.update(1500);
        rightServo.update(1500);
        accelerate = false;
        stop = false;
    }

    public int currentSpeedPercentage()
    {
        int maxSpeed = 200;
        int leftServoSpeed = Math.abs(1500 - leftServo.getPulseWidth());
        int rightServoSpeed = Math.abs(1500 - rightServo.getPulseWidth());
        return ((leftServoSpeed > rightServoSpeed ? leftServoSpeed : rightServoSpeed) / 200) * 100;
    }

    public int currentSpeed()
    {
        int leftServoSpeed = Math.abs(1500 - leftServo.getPulseWidth());
        int rightServoSpeed = Math.abs(1500 - rightServo.getPulseWidth());
        return leftServoSpeed > rightServoSpeed ? leftServoSpeed : rightServoSpeed;
    }

    public void goSlowToSpeed(int speedPercentage)
    {
        int maxSpeed = 200;
        speedPercentage1 = speedPercentage;
        int speedUnit = 2;
        int speed = speedPercentage * speedUnit;

        targetSpeedLeft = 1500 - speed;
        targetSpeedRight = 1500 + speed;

        int currentSpeedRight = leftServo.getPulseWidth();
        int currentSpeedLeft = rightServo.getPulseWidth();

        int speedDifferenceLeft = targetSpeedLeft - currentSpeedLeft;
        int speedDifferenceRight = targetSpeedRight - currentSpeedRight;

        accelerate = true;
    }

    public void goSpeedToSlow(int speedPercentage)
    {

        int maxSpeed = 200;
        speedPercentage1 = speedPercentage;
        int speedUnit = 2;
        int speed = speedPercentage * speedUnit;

        targetSpeedLeft = 1500;
        targetSpeedRight = 1500;

        int currentSpeedRight = leftServo.getPulseWidth();
        int currentSpeedLeft = rightServo.getPulseWidth();

        int speedDifferenceLeft = targetSpeedLeft - currentSpeedLeft;
        int speedDifferenceRight = targetSpeedRight - currentSpeedRight;

        stop = true;
    }
}