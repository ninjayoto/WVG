import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Referers
{
    private static ArrayList<String> arr = new ArrayList<String>();

    public static void initialize(String filename)
    {
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                arr.add(line);
            }
        }
        catch (Exception e)
        {
        }
    }
    
    public static String[] referer(int size)
    {
        String[] referers = new String[size];
        int random;
        for (int i = 0; i < referers.length; i++)
        {
            random = (int)(Math.random() * arr.size());
            referers[i] = arr.get(random);
        }
        return referers;
    }
}
