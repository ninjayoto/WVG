public class ViewGenerator
{
    public static void main (String [] args) throws Exception
    {
        Referers.initialize();
        if (args.length == 0)
        {
            Gui.start();
        }
        else
        {
            Cli cli = new Cli(args);
            cli.parse();
        }
    }
}
