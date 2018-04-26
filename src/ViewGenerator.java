import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ViewGenerator
{
    public static int progress;
    public static final String VERSION = "1.1.0";

    public static void main (String [] args) throws Exception
    {
        if (args.length == 0)
        {
            Gui.start();
        }
        else
        {
            {
                Cli cli = new Cli(args);
                cli.parse();
            }
        }
    }

    public static String linkString(String site)
    {
        if (!site.substring(0, 7).equals("http://"))
        {
            if (site.substring(0,8).equals("https://"))
            {
                site = site.substring(8);
                site = "http://" + site;
            }
            else
            {
                site = "http://" + site;
            }
        }
        return site;
    }

    public static boolean isUpdate()
    {
        try
        {
            Proxies.newProxy();
            System.getProperties().put( "proxySet", "true" );
            System.getProperties().put( "socksProxyHost", "127.0.0.1" );
            System.getProperties().put( "socksProxyPort", "9050" );
            System.setProperty("socksProxyVersion" , "5");
            URL url = new URL("https://raw.githubusercontent.com/BitsByWill/WVG/master/src/CURRENT_VER");
            URLConnection con = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            String newVersion = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                newVersion += line;
            }
            Proxies.stopProxy();
            if (!newVersion.equals(VERSION))
            {
                return true;
            }
        }
        catch (Exception e)
        {
        }
        return false;
    }

    public static void update(boolean windows) throws Exception
    {
        if (windows)
        {
            Process p = Runtime.getRuntime().exec("resources\\wintor\\check.exe");
        }
        else
        {
            Process p = Runtime.getRuntime().exec("resources/linuxtor/check");
        }
    }
}
