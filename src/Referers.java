import java.io.BufferedReader;
import java.io.FileReader;

public class Referers
{
    private static String[] arr = new String[5000];

    public static void initialize()
    {
        int counter = 0;
        try
        {
            FileReader fr = new FileReader("5000sites");
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                arr[counter] = line;
                counter++;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error! \n" + e);
        }
    }
    
    public static String[] referer(int size)
    {
        String[] referers = new String[size];
        int random;
        for (int i = 0; i < referers.length; i++)
        {
            random = (int)(Math.random() * 5000) + 1;
            referers[i] = arr[random];
        }
        return referers;
    }
}
