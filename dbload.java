import java.io.*;
import java.nio.ByteBuffer;

public class dbload {

    public static void main(String[] args) {
        dbload newLoader = new dbload();

        long startTime = System.currentTimeMillis();
        newLoader.inputArguments(args);
        long endTime = System.currentTimeMillis();

        System.out.println("Total time taken: " + (endTime-startTime));

    }


    public void inputArguments(String[] args){

        if (args[0].equals("-p")){

            inputFileReading(args[2],Integer.parseInt(args[1]));

        }
        else
        {
            System.out.println("Arguments different than expected!");
        }
    }

    public void inputFileReading(String fName, int pSize) {
        dbRecord newEntity = new dbRecord();
        File newHeapFile = new File("./heap." + pSize);

        FileOutputStream fos = null;
        BufferedReader reader = null;
        int counter=0,pgCount=0, recCount=0;
        byte[] recordInBytes = new byte[dbRecord.sizeOfRecord];
        try {

            fos = new FileOutputStream(newHeapFile);
            reader = new BufferedReader(new FileReader(fName));
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                String recordEntry[] = line.split("," , -1);

                recordInBytes = newEntity.serialize(recordInBytes, recordEntry, counter);

                counter++;
                fos.write(recordInBytes);
                if ( newEntity.sizeOfRecord > pSize/(counter+1) ){
                    addExtras(fos,pSize,counter,pgCount);

                    counter = 0;
                    pgCount++;
                }
                recCount++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {


            if(fos!=null) {
                try {
                    if ((reader.readLine()) == null)
                    {
                        addExtras(fos,pSize,counter,pgCount);
                        pgCount++;
                    }
                    fos.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println("Page Count: " + pgCount);
        System.out.println("Record Count: " + recCount);

    }





    public void addExtras(FileOutputStream fos, int pageSize, int count, int pgCount)
            throws IOException
    {
        byte[] filePadding = new byte[pageSize-(dbRecord.sizeOfRecord*count)-4];


        ByteBuffer bBuffer = ByteBuffer.allocate(4);
        bBuffer.putInt(pgCount);

        fos.write(filePadding);
        fos.write(bBuffer.array());
    }




}
