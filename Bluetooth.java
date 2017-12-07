import TI.*;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.util.*;


public class Bluetooth
{
    private final SerialConnection conn;
    private final int BAUDRATE = 115200;
    private ArrayList<Integer> data;
    
    public Bluetooth()
    {
        conn = new SerialConnection(BAUDRATE);
        data = new ArrayList<>();
    }
    
    public void update()
    {
        if (conn.available() > 0) data.add(conn.readByte());            
        else data.clear();
    }
    
    public boolean dataReady()
    {
        return (conn.available() == 0 && data.size() > 0);
    }
    public ArrayList<Integer> getData()
    {
        return data;
    }
}
