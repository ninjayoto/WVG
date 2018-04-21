import org.apache.commons.lang3.SystemUtils;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.validator.routines.UrlValidator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.io.File;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.UIManager;

//performance options will be added in the future
public class Gui
{
    public static void start()
    {
        UIManager.put("ProgressBar.selectionForeground", Color.RED);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = screenSize.width/2;
        int centerY = screenSize.height/2;
        JWindow window = new JWindow();
        JWindow loading = new JWindow();
        JWindow generator = new JWindow();
        ImageIcon main = new ImageIcon("resources/main.jpg");
        ImageIcon generate = new ImageIcon("resources//generating.png");
        ImageIcon splash = new ImageIcon("resources//splash.png");
        ImageIcon load = new ImageIcon("resources//loading.gif");
        window.getContentPane().add(new JLabel("", splash, SwingConstants.CENTER));
        generator.getContentPane().add(new JLabel("", generate, SwingConstants.CENTER));
        loading.getContentPane().add(new JLabel("", load, SwingConstants.CENTER));
        loading.setBackground(new Color(0, 0, 0, 0));
        loading.setBounds(centerX - 250, centerY, 500, 500);
        window.setBounds(0, 0, 500, 500);
        window.setLocationRelativeTo(null);
        window.setBackground(new Color(0,0,0,0));
        generator.setBounds(0, 0, 500, 500);
        generator.setLocationRelativeTo(null);
        generator.setBackground(new Color(0,0,0,0));
        window.setVisible(true);
        loading.setVisible(true);
        try 
        {
            Thread.sleep(3000);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        window.setVisible(false);
        loading.setVisible(false);
        Object [] options = {"Continue", "Exit"};
        JFileChooser userAgentSelect = new JFileChooser();
        JFileChooser refSelect = new JFileChooser();
        JTextField website = new JTextField("Website for Views - make sure to include the http portion", 20);
        website.setPreferredSize( new Dimension( 10, 24 ));
        JTextField timesRun = new JTextField("Number of Views", 20);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension( 800, 550 )); 
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        String[] perfOptions = {"Very Low (Default)", "Low", "Medium", "High", "Very High"};
        JComboBox dropdown = new JComboBox(perfOptions);
        panel.add(new JLabel("Site"));
        panel.add(website);
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Views"));
        panel.add(timesRun);
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("User-Agent List"));
        panel.add(new JLabel("Blank for Default"));
        panel.add(userAgentSelect);
        panel.add(new JLabel("Referer List"));
        panel.add(new JLabel("Blank for Default"));
        panel.add(refSelect);
        //panel.add(new JLabel("Performance"));
        //panel.add(dropdown);
        //panel.add(new JLabel("\n"));
        //String perfLevel = null;
        boolean urlValid = true;
        boolean timeValid = true;
        boolean goBack = true;
        UrlValidator urlvalid = new UrlValidator();
        String site = null;
        String timeStr;
        int times = 0;
        String userAgent = null;
        String ref = null;
        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);
        progressBar.setBorder(null);
        JWindow generating = new JWindow();
        generating.getContentPane().add(progressBar, BorderLayout.SOUTH);
        generating.setBounds(0, 0, 500, 500);
        generating.setLocationRelativeTo(null);
        generating.setBackground(new Color(0,0,0,0));
        try
        {
            do
            {
                if (goBack)
                {
                    int n = JOptionPane.showOptionDialog(null, scrollPane, "WVG (Will's View Generator)", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, main, options, options[0]);
                    if (n == 0)
                    {
                        if (userAgentSelect.getSelectedFile() != null)
                        {
                            userAgent = userAgentSelect.getSelectedFile().toString();
                        }
                        if (refSelect.getSelectedFile() != null)
                        {
                            ref = refSelect.getSelectedFile().toString();
                        }
                        //perfLevel = dropdown.getSelectedItem().toString();
                        site = website.getText();
                        timeStr = timesRun.getText();
                        if (urlvalid.isValid(site))
                        {
                            linkString(site);
                            urlValid = true;
                        }
                        else
                        {
                            urlValid = false;
                            goBack = false;
                        }
                        try
                        {
                            times = Integer.parseInt(timeStr);
                            if (times <= 0)
                            {
                                timeValid = false;
                                goBack = false;
                            }
                            else
                            {
                                timeValid = true;
                            }
                        }
                        catch (Exception e)
                        {
                            timeValid = false;
                            goBack = false;
                        }
                    }
                    else
                    {
                        System.exit(0);
                    }
                }
                else
                {
                    if (!urlValid && !timeValid)
                    {
                        JOptionPane.showMessageDialog(null, "Invalid link and times... please try again", "Will's View Generator", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (!urlValid)
                    {
                        JOptionPane.showMessageDialog(null, "Invalid link... please try again", "Will's View Generator", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid times... please try again", "Will's View Generator", JOptionPane.ERROR_MESSAGE);
                    }
                    goBack = true;
                }
            }while (!urlValid || !timeValid);
            String important = "IMPORTANT!\nThis program is not responsible your IP getting blacklisted for bot activity.\nThis program uses random user-agents, referers, and the TOR onion network\n(including some other proxies) to hide your computer's identity and generate realistic views.\nThis product is produced independently from the Tor anonymity software and carries\nno guarantee from The Tor Project about quality, suitability or anything else.\nLearn more at https://www.torproject.org/.";
            JOptionPane.showMessageDialog(null, important, "Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
            generator.setVisible(true);
            generator.toFront();
            generating.setVisible(true); //FIX THIS PART
            generating.toFront();
            if (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_LINUX)
            {
                Bot bot;
                if (userAgent == null && ref == null)
                {
                    bot = new Bot (times, site, null, null);
                }
                else if (userAgent == null)
                {
                    bot = new Bot (times, site, userAgent, null);
                }
                else if (ref == null)
                {
                    bot = new Bot (times, site, null, ref);
                }
                else
                {
                    bot = new Bot (times, site, userAgent, ref);
                }
                bot.run();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "This program does not work on this system yet.","Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
            }
            JOptionPane.showMessageDialog(null, "Views Generated!  Thanks for using this program!", "Will's View Generator", JOptionPane.INFORMATION_MESSAGE, main);
        }
        catch (Exception e)
        {
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
}
