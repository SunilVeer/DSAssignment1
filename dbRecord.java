import java.io.*;
import java.nio.ByteBuffer;

public class dbRecord {

    public static final int sizeOfRecId = 4;


    public static final int sizeOfId = 4;
    public static final int sizeOfDateTime = 26; 
    public static final int sizeOfYear = 4; 
    public static final int sizeOfMonth = 9; 
    public static final int sizeOfDay = 9; 
    public static final int sizeOfMdate = 4;
    public static final int sizeOfTime = 4;
    public static final int sizeOfSensorId = 4;
    public static final int sizeOfSensorName = 42;
    public static final int sizeOfHourlyCount = 4;

    public static final int sizeOfRecord = sizeOfRecId+sizeOfId+sizeOfDateTime+sizeOfYear+sizeOfMonth+sizeOfMdate+sizeOfDay+sizeOfTime+sizeOfSensorId+sizeOfSensorName+sizeOfHourlyCount;


    public static final int offset_recordId = 0;
    public static final int offset_id = offset_recordId + sizeOfRecId;
    public static final int offset_dateTime = offset_id + sizeOfId;
    public static final int offset_year = offset_dateTime + sizeOfDateTime;
    public static final int offset_month = offset_year + sizeOfYear;
    public static final int offset_mdate = offset_month + sizeOfMonth;
    public static final int offset_day = offset_mdate + sizeOfMdate;
    public static final int offset_time = offset_day + sizeOfDay;
    public static final int offset_sensorId = offset_time + sizeOfTime;
    public static final int offset_sensorName = offset_sensorId + sizeOfSensorId;
    public static final int offset_hourlyCount = offset_sensorName + sizeOfSensorName;


    public void copyInt(String entry, int capacity, int offset, byte[] rec)

    {
        int recordEntry = Integer.parseInt(entry);

        ByteBuffer newByteBuffer =ByteBuffer.allocate(capacity);

        newByteBuffer.putInt(recordEntry);
        byte[] recordData = newByteBuffer.array();

        System.arraycopy(recordData, 0, rec, offset, capacity);
    }

    public void copyString(String entry, int capacity, int offset, byte[] rec)
            throws UnsupportedEncodingException
    {

        byte[] recordData = new byte[capacity];
        byte[] source = entry.trim().getBytes("utf-8");
        if (entry != "")
        {
            System.arraycopy(source, 0,
                    recordData, 0, source.length);
        }
        System.arraycopy(recordData, 0, rec, offset, recordData.length);
    }


    byte[] serialize(byte[] record, String[] in, int outCount) throws UnsupportedEncodingException {
        copyInt(    String.valueOf(outCount),             sizeOfRecId         , offset_recordId         , record);
        copyInt(    in[0],           sizeOfId          , offset_id          , record);
        copyString( in[1],         sizeOfDateTime    , offset_dateTime    , record);
        copyInt(    in[2],         sizeOfYear        , offset_year        , record);
        copyString( in[3],        sizeOfMonth       , offset_month       , record);
        copyInt(    in[4],        sizeOfMdate       , offset_mdate       , record);
        copyString( in[5],          sizeOfDay         , offset_day         , record);
        copyInt(    in[6],         sizeOfTime        , offset_time        , record);
        copyInt(    in[7],     sizeOfSensorId    , offset_sensorId    , record);
        copyString( in[8],   sizeOfSensorName  , offset_sensorName  , record);
        copyInt(    in[9],  sizeOfHourlyCount , offset_hourlyCount , record);
        return record;
    }


}
