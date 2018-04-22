public class ViewGenerator
{
    public static int progress;
    public static final String VERSION = "1.1.0";

    public static void main (String [] args) throws Exception
    {
        Referers.initialize();
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
}
