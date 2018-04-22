public class ViewGenerator
{
    public static int progress;
    public static String version = "1.1.0";
    
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
}
