import TI.*;
import java.util.*;


public class Bluetooth
{
    private final SerialConnection conn;
    private final int BAUDRATE = 115200;
    private ArrayList<Integer> readData;
    private Queue<Byte> writeQueue;
    
    public Bluetooth()
    {
        conn = new SerialConnection(BAUDRATE);
        readData = new ArrayList<>();
        writeQueue = new LinkedList<>();
    }
    
    public void update()
    {
        if (writeQueue.size() > 0) conn.writeByte(writeQueue.remove());
        if (conn.available() > 0) readData.add(conn.readByte());            
        else readData.clear();
    }
    
    public boolean dataReady()
    {
        return (conn.available() == 0 && readData.size() > 0);
    }
    public ArrayList<Integer> getData()
    {
        return readData;
    }
    
    public void writeData(byte[] data) // java reference?
    {
        // writeQueue = new LinkedList<>(Arrays.asList(data)); ?? waarom werkt dit niet
        writeQueue.clear();
        for (int i = 0; i < data.length; i++)
        {
            writeQueue.add(data[i]);
        }

        conn.writeByte(writeQueue.remove());
    }
    
    public boolean isWriting()
    {
        return writeQueue.size() > 0;
    }
}
