package playlisttracker;

import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Reads lines of data from a file and then is able to manipulate data in
 * various ways. Data is treated as String variables.
 *
 * @author Matt Babel
 */
public class FileData {
    private String path;
    private List<String> data = new ArrayList<>();
    
    // list of Integers that are paired with data
    private List<Integer> dataTag = new ArrayList<>();
    private int lastDataPosition = 0;

    /**
     * Constructor, reads data and puts it into a list
     *
     * @param filePath address of the data file
     */
    public FileData(String filePath) {
        path = this.getClass().getResource(filePath).getPath();
        if(path.contains("%20")) {
            path = path.replaceAll("%20", " ");
        }

        if (filePath.substring(filePath.length()-4, filePath.length()).equals(".txt")) { // txt file
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                                    new FileInputStream(path), "UTF-8"));
                
                String line;
                while ((line = br.readLine()) != null) {
                    data.add(line);
                }

                for (int i = 0; i < data.size(); i++) {
                    dataTag.add(0);
                }
                br.close();
            } catch (FileNotFoundException ex) {
                System.out.println("FileData not found - " + path);
            } catch (IOException ex) {
                System.out.println("IOExeption in Constructor : FileData");
            }
        } else {                                     // folder
            File folder = new File(path);
            for (File fileEntry : folder.listFiles()) {
                data.add(fileEntry.getName());
            }
        }
    }
    

    /**
     * Gets data by position
     *
     * @param position
     * @return data at position
     */
    public String get(int position) {
        lastDataPosition = position;
        return data.get(lastDataPosition);
    }

    /**
     * returns the position of a specific data in data file
     *
     * @param specificData
     * @return position of specificData in data file
     */
    public int getPosition(String specificData) {
        return data.indexOf(specificData);
    }

    /**
     * Gets dataTag by position
     *
     * @param position
     * @return dataTag at position
     */
    public int getTag(int position) {
        return dataTag.get(position);
    }

    /**
     * Gets dataTag of specificData
     *
     * @param specificData
     * @return dataTag of specificData
     */
    public int getTag(String specificData) {
        return dataTag.get(this.getPosition(specificData));
    }

    /**
     * @return random data
     */
    public String getRandom() {
        Random rand = new Random();
        lastDataPosition = rand.nextInt(data.size());
        return data.get(lastDataPosition);
    }

    /**
     * @return the last position used
     */
    public int getLastPosition() {
        return lastDataPosition;
    }

    /**
     * adds a new piece of data
     *
     * @param newData
     */
    public void add(String newData) {
        dataTag.add(0);
        data.add(newData);
    }

    /**
     * adds one to dataTag at position
     *
     * @param position
     */
    public void tickTag(int position) {
        dataTag.set(position, dataTag.get(position) + 1);
    }

    /**
     * adds one to dataTag of specificData
     *
     * @param specificData
     */
    public void tickTag(String specificData) {
        dataTag.set(this.getPosition(specificData),
                dataTag.get(this.getPosition(specificData)) + 1);
    }

    /**
     * removes specific data
     *
     * @param specificData
     */
    public void delete(String specificData) {
        dataTag.remove(this.getPosition(specificData));
        data.remove(specificData);
    }

    /**
     * removes data by position
     *
     * @param position
     */
    public void delete(int position) {
        dataTag.remove(position);
        data.remove(position);
    }

    /**
     * changes specific data into new data
     * @param specificData
     * @param newData 
     */
    public void changeData(String specificData, String newData) {
        data.set(data.indexOf(specificData), newData);
    }
    /**
     * changes data by position into new data
     * @param position of data to change
     * @param newData 
     */
    public void changeData(int position, String newData) {
        data.set(position, newData);
    }
    
    /**
     * @return the number of elements in data
     */
    public int size() {
        return data.size(); 
    }
    /**
     * saves the data to the file
     */
    public void save() {
        File dataFile = new File(path);
        File tempFile = new File(path.substring(0, path.lastIndexOf("/"))+"/myTempFile.txt");

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(tempFile), "UTF-8")); 

            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
            dataFile.delete();
            tempFile.renameTo(dataFile);
        } catch (IOException ex) {
            System.out.println("IOExceptoin in save1 : FileData");
        }
    }

    /**
     * saves the data with a new file path
     * @param newFilePath
     */
    public void save(String newFilePath) {
        File deletedFile = new File(path);
        deletedFile.delete();
        path = newFilePath;
        File dataFile = new File(path);
        File tempFile = new File(path.substring(0, path.lastIndexOf("/"))+"/myTempFile.txt");
        
        
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(tempFile), "UTF-8")); 

            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
            dataFile.delete();
            tempFile.renameTo(dataFile);
        } catch (IOException ex) {
            System.out.println("IOException in save2 : FileData");
        }
    }
    /**
     * outputs the data to the console
     */
    public void outputToConsole() {
        for (String line : data) {
            System.out.println(line);
        }
    }

    /**
     * erases all data
     */
    public void clear() {
        data.clear();
    }
    /**
     * Parses the number from the last space to the second to last space
     */
    public int getLastMonthSize() {
        
        boolean stop = false;
        int x = 1;
        
        while(stop == false) {
           x++;
           
           String line = data.get(data.size() - x);
           if (line.equals("space")) {
               stop = true;
           } 
        }
        
        return x;
    }
}
