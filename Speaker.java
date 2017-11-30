import TI.*;

public class Speaker
{
    int speakerPin;
    int freq;
    int time;
    Timer soundTimer;
    boolean soundOn;
    public Speaker()
    {
        soundOn = false;
    }
    
    public void update()
    {
        if(soundTimer.timeout() && soundOn == true)
        {
            BoeBot.freqOut(speakerPin,freq,time);
        }
    }
    
    public void sound(int freq, int time)
    {
        this.speakerPin = speakerPin;
        BoeBot.freqOut(speakerPin,freq,time);
    }
    
    public void soundTime(int freq, int time, int timeCourse)
    {
        soundTimer = new Timer(timeCourse);
        this.freq = freq;
        this.time = time;
        soundOn = true;
    }
    
    public void stop()
    {
        soundOn = false;
    }
}
