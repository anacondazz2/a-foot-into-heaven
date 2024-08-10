import java.util.*;
import java.io.*;

/**
 * Attempted use of files to permanently save the array of Ally objects. However did not work.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Writer  
{
    private int x;

    public Writer() {

    }

    static void writeToFile(String fileName, ArrayList<Ally> allies) throws IOException {
        File f = new File(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(allies);
        oos.flush();
        oos.close();    
    }

    static ArrayList<Ally> readAllies(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream objinstream = new ObjectInputStream(new FileInputStream("file1.txt"));  
        return (ArrayList<Ally>)objinstream.readObject();      
    }
}
