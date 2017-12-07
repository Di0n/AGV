import TI.*;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.util.*;


public class Bluetooth
{
    final SerialConnection conn;
    final int BAUDRATE = 115200;
    private ArrayList<Integer> data;
    
    public Bluetooth()
    {
        conn = new SerialConnection(BAUDRATE);
        data = new ArrayList<>();
    }
    
    public void update()
    {
        if (conn.available() > 0) data.add(conn.readByte());
    }
    
    public ArrayList<Integer> getData()
    {
        return data;
    }
}
