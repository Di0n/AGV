import TI.*;
public class RemoteControl
{
    private Remote remote;
    private Transmission transmission;
    private boolean remoteControlEngaged;
    
    public RemoteControl(Transmission transmission, boolean remoteControlEngaged)
    {
        remote = new Remote();
        this.transmission = transmission;
        this.remoteControlEngaged = remoteControlEngaged;
    }
    
    public void update()
    {
        remote.update();
        
        Remote.RemoteButton button = remote.getKeyPressed();
        //if (!remoteControlEngaged && button != Remote.RemoteButton.ENABLE_REMOTE_CONTROL)  
        //    return; // navragen
        if (!remoteControlEngaged && (button == Remote.RemoteButton.DISABLE_REMOTE_CONTROL || button == Remote.RemoteButton.NONE))
            return;
            
        remoteControlEngaged = true;
        switch (button)
        {
            case FORWARD:
                //transmission.goToSpeed(50);
                transmission.goSlowToSpeed(100);
            break;
            case BACKWARDS:
                //transmission.goToSpeed(-50);
                transmission.goSlowToSpeed(-100);
            break;
            case LEFT:
                //transmission.turnDegrees(-90, 50);
                transmission.turnLeft();
            break;
            case RIGHT:
                //transmission.turnDegrees(90, 50);
                transmission.turnRight();
            break;
            case STOP:
                transmission.emergencyBrake(); 
                //transmission.goSlowToSpeed(1);
            break;
            case ENABLE_REMOTE_CONTROL:
                remoteControlEngaged = true;
            break;
            case DISABLE_REMOTE_CONTROL:
                remoteControlEngaged = false;
            break;
            case TURN_EIGHT:
                //transmission.turnEight();
            break;
            default:
            break;
        }
    }
    
    public boolean isRemoteControlEnabled()
    {
        return remoteControlEngaged;
    }
}
