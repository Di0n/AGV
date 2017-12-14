import TI.*;
import java.util.*;
import java.nio.charset.Charset;

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
    
    private void writeDataDirectly(byte[] data)
    {
        writeQueue.clear();
        for (int i = 0; i < data.length; i++)
            conn.writeByte(data[i]);
    }
    private void writeData(byte[] data) // java reference?
    {
        // writeQueue = new LinkedList<>(Arrays.asList(data)); ?? waarom werkt dit niet
        writeQueue.clear();
        for (int i = 0; i < data.length; i++)
        {
            writeQueue.add(data[i]);
        }

        conn.writeByte(writeQueue.remove());
    }
    
    public void writeString(String data)
    {
        String temp = ">" + data + '\0';
        if (data.length() > 1000)
            writeData(data.getBytes(Charset.forName("UTF-8")));
        else writeDataDirectly(data.getBytes(Charset.forName("UTF-8")));
    }
    
    public void sendAcknowledge()
    {
        writeDataDirectly(new byte[] {0x3e, 0x06, 0x00});
    }
    
    public boolean isWriting()
    {
        return writeQueue.size() > 0;
    }
}
