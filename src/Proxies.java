import org.apache.commons.lang3.SystemUtils;

public class Proxies
{
    public static void newProxy()
    {
        if (SystemUtils.IS_OS_WINDOWS)
        {
            try
            {
                Process p = Runtime.getRuntime().exec("resources\\wintor\\runtor.exe");
                p.waitFor();
            }
            catch (Exception e)
            {
            }
        }
        else
        {
            try
            {
                Process p = Runtime.getRuntime().exec("resources/linuxtor/runtor");
                p.waitFor();
            }
            catch (Exception e)
            {
            }
        }
    }

    public static void stopProxy()
    {
        if (SystemUtils.IS_OS_WINDOWS)
        {
            try
            {
                Process p = Runtime.getRuntime().exec("resources\\wintor\\stoptor.exe");
                p.waitFor();
            }
            catch (Exception e)
            {
            }
        }
        else
        {
            try
            {
                Process p = Runtime.getRuntime().exec("resources/linuxtor/stoptor");
                p.waitFor();
            }
            catch (Exception e)
            {
            }
        }
    }
}