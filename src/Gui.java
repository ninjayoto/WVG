import org.apache.commons.lang3.SystemUtils;
import javax.swing.*;
import java.awt.*;
import org.apache.commons.validator.routines.UrlValidator;

public class Gui
{
    public static void start()
    {
        ImageIcon main = new ImageIcon("resources/main.jpg");
        JOptionPane.showMessageDialog(null, "Welcome to Will's View Generator!", "Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
        boolean url = true;
        UrlValidator urlvalid = new UrlValidator();
        String site;
        do
        {
            if (url)
            {
                site = (String) JOptionPane.showInputDialog(null, "Website to generate views for (make sure to include the \"www.\" portion): ", "Will's View Generator", JOptionPane.PLAIN_MESSAGE, main, null, null);   
                if (urlvalid.isValid(site) || site.length() > 4)
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
                }
                else
                {
                    url = false;
                }
            }
            else
            {
                site = (String) JOptionPane.showInputDialog(null, "Invalid link... try again (make sure to include the \"www.\" portion): ", "Will's View Generator", JOptionPane.PLAIN_MESSAGE, main, null, null);
                if (urlvalid.isValid(site) || site.length() > 4)
                {
                    url = true;
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
                }
                else
                {
                    url = false;
                }
            }
        }while (!url);
        boolean timesValid = true;
        String timeStr;
        int times = 0;
        do
        {
            if (timesValid)
            {
                try
                {
                    timeStr = (String)JOptionPane.showInputDialog(null, "How many views do you want?", "Will's View Generator", JOptionPane.PLAIN_MESSAGE, main, null, null);
                    times = Integer.parseInt(timeStr);
                    if (times <= 0)
                    {
                        timesValid = false;
                    }
                    else
                    {
                        timesValid = true;
                    }
                }
                catch (Exception e)
                {
                    timesValid = false;
                }
            }
            else
            {
                try
                {
                    timeStr = (String)JOptionPane.showInputDialog(null, "That's not a valid value... try again: ", "Will's View Generator", JOptionPane.PLAIN_MESSAGE, main, null, null);
                    times = Integer.parseInt(timeStr);
                    if (times <= 0)
                    {
                        timesValid = false;
                    }
                    else
                    {
                        timesValid = true;
                    }
                }
                catch (Exception e)
                {
                    timesValid = false;
                }
            }
        }while (!timesValid);
        String important = "IMPORTANT!\nThis program is not responsible your IP getting blacklisted for bot activity.\nThis program uses random user-agents, referers, and the TOR onion network\n(including some other proxies) to hide your computer's identity and generate realistic views.\nThis product is produced independently from the Tor anonymity software and carries\nno guarantee from The Tor Project about quality, suitability or anything else.\nLearn more at https://www.torproject.org/.";
        JOptionPane.showMessageDialog(null, important, "Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
        JOptionPane.showMessageDialog(null, "This windows will close for now and the program will let you know when the process has finished.", "Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
        if (SystemUtils.IS_OS_WINDOWS)
        {
            WinBot winbot = new WinBot(times, site);
            winbot.run();
        }
        else if (SystemUtils.IS_OS_LINUX)
        {
            LinuxBot linuxbot = new LinuxBot(times, site);
            linuxbot.run();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "This program does not work on this system yet.","Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
        }
        JOptionPane.showMessageDialog(null, "Views Generated!  Thanks for using this program!", "Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
    }
}