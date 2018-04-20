import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.lang3.SystemUtils;

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
        options.addOption("m", "multithread", true, "The number of threads you want");
        //maybe add update referals, remember to include in help if add
    }

    public void parse()
    {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try
        {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h"))
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
                    if (time >= 0)
                    {
                        run(site, time);
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

    private void run(String site, int time)
    {
        String important = "IMPORTANT!\nThis program is not responsible your IP getting blacklisted for bot activity.\nThis program uses random user-agents, referers, and the TOR onion network\n(including some other proxies) to hide your computer's identity and generate realistic views.\nThis product is produced independently from the Tor anonymity software and carries\nno guarantee from The Tor Project about quality, suitability or anything else.\nLearn more at https://www.torproject.org/.";
        System.out.println(important);
        System.out.println("Now please wait while the program generates views...");
        if (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_LINUX)
        {
            Bot bot = new Bot (time, site, null, null); //not finished yet here
            bot.run();
        }
        else
        {
            System.out.println("This program does not support this OS.");
        }
        System.out.println("Thanks for using this program!");
        System.exit(0);
    }

    private void help()
    {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp(" ", options);
        System.exit(0);
    }
}
