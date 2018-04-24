import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.lang3.SystemUtils;
import java.util.concurrent.TimeUnit;
import java.text.DecimalFormat;
import java.io.File;

public class Cli
{
    private String[] args;
    private Options options = new Options();

    public Cli(String[] args)
    {
        this.args = args;
        options.addOption("h", "help", false, "Shows help for this program.");
        options.addOption("w", "website", true, "The website you want to generate views for. Make sure to enter a proper url.");
        options.addOption("t", "time", true, "The number of views you want for the site.");
        options.addOption("u", "useragents", true, "Your custom list of user-agents. Do not use this option if you want the default list.");
        options.addOption("r", "referers", true, "Your custom list of referers. Do not use this option if you want the default list.");
        options.addOption("v", "version", false, "Shows program version.");
        //options.addOption("m", "multithread", true, "The number of threads you want");
        //maybe add update referals, remember to include in help if add
    }

    public void parse()
    {
        if (!SystemUtils.IS_OS_WINDOWS && !SystemUtils.IS_OS_LINUX)
        {
            System.out.println("WVG does not support this OS as of now.");
            System.exit(0);
        }
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try
        {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h") && cmd.hasOption("v"))
            {
                System.out.println("Version: " + ViewGenerator.VERSION);
                help();
            }
            else if (cmd.hasOption("v"))
            {
                System.out.println("Version: " + ViewGenerator.VERSION);
                System.exit(0);
            }
            else if (cmd.hasOption("h"))
            {
                help();
            }
            else if (cmd.hasOption("w") && cmd.hasOption("t"))
            {
                UrlValidator urlvalid = new UrlValidator();
                String site = "";
                int time = -1;
                try
                {
                    site = cmd.getOptionValue("w");
                }
                catch (Exception e)
                {
                    help();
                }
                try
                {
                    time = (int)(Integer.parseInt(cmd.getOptionValue("t")));
                }
                catch (Exception e)
                {
                    help();
                }
                if (urlvalid.isValid(site))
                {
                    site = ViewGenerator.linkString(site);
                    if (time >= 0)
                    {
                        if (cmd.hasOption("u") && cmd.hasOption("r"))
                        {
                            if (new File(cmd.getOptionValue("u")).exists() && new File(cmd.getOptionValue("r")).exists())
                            {
                                run (site, time, cmd.getOptionValue("u"), cmd.getOptionValue("r"));
                            }
                            else
                            {
                                dneFile();
                            }
                        }
                        else if (cmd.hasOption("u"))
                        {
                            if (new File(cmd.getOptionValue("u")).exists())
                            {
                                run (site, time, cmd.getOptionValue("u"), null);
                            }
                            else
                            {
                                dneFile();
                            }
                        }
                        else if (cmd.hasOption("r"))
                        {
                            if (new File(cmd.getOptionValue("r")).exists())
                            {
                                run (site, time, null, cmd.getOptionValue("r"));
                            }
                            else
                            {
                                dneFile();
                            }
                        }
                        else
                        {
                            run (site, time, null, null);
                        }
                    }
                }
                else
                {
                    help();
                }
            }
            else
            {
                help();
            }
        }
        catch (Exception e)
        {
            help();
        }
    }

    private void run(String site, int times, String userAgent, String ref)
    {
        String important = "IMPORTANT!\nThis program is not responsible your IP getting blacklisted for bot activity.\nThis program uses random user-agents, referers, and the TOR onion network\n(including some other proxies) to hide your computer's identity and generate realistic views.\nThis product is produced independently from the Tor anonymity software and carries\nno guarantee from The Tor Project about quality, suitability or anything else.\nLearn more at https://www.torproject.org/.";
        System.out.println(important);
        System.out.println("Now please wait while the program generates views...");
        Bot bot = new Bot(times, site, userAgent, ref);
        bot.start();
        System.out.println();
        String[] slashes = {"/", "|", "\\", "--"};
        int index = 0;
        while (ViewGenerator.progress < times) 
        {
            int temp = (int)(((double)ViewGenerator.progress / times) * 100);
            DecimalFormat df = new DecimalFormat("#.00");
            double perc = ((double)ViewGenerator.progress / times) * 100;
            if (index == 4)
            {
                index = 0;
            }
            if (ViewGenerator.progress == 0)
            {
                System.out.print("\rGenerated " + ViewGenerator.progress + " out of " + times + " view(s). (0.00%)  " + slashes[index]);
            }
            else
            {
                System.out.print("\rGenerated " + ViewGenerator.progress + " out of " + times + " view(s).  (" + df.format(perc) + "%)  " + slashes[index]);
            }
            index++;
            try 
            {
                Thread.sleep(100);
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
                break;
            }
        }
        try
        {
            bot.join();
            System.out.println("Views Generated!");
            System.out.println("Thanks for using WVG!");
        }
        catch (Exception e)
        {
            System.out.println("An unknown error occurred... WVG will exit.");
        }
        System.exit(0);
    }

    private void dneFile()
    {
        System.out.println("One or more of the files specified does not exist");
        System.exit(0);
    }
    
    private void help()
    {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp(" ", options);
        System.exit(0);
    }
}
