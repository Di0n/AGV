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
                transmission.goSlowToSpeed(30);
            break;
            case BACKWARD:
                //transmission.goToSpeed(-50);
                transmission.goSlowToSpeed(-30);
            break;
            case FWD_LEFT:
                transmission.turnDegrees(-45, 30);
            break;
            case LEFT:
                transmission.turnDegrees(-90, 30);
                //transmission.turnLeft();
            break;
            case BWD_LEFT:
                transmission.turnDegrees(-45, -30);
            break;
            case FWD_RIGHT:
                transmission.turnDegrees(45, 30);
            break;
            case RIGHT:
                transmission.turnDegrees(90, 30);
                //transmission.turnRight();
            break;
            case BWD_RIGHT:
                transmission.turnDegrees(45, -30);
            case STOP:
                transmission.goSpeedToSlow(-100);
                //transmission.goSlowToSpeed(1);
            break;
            case EMERGENCY_BRAKE:
                transmission.emergencyBrake();
            break;
            case ENABLE_REMOTE_CONTROL:
                remoteControlEngaged = true;
            break;
            case DISABLE_REMOTE_CONTROL:
                remoteControlEngaged = false;
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
