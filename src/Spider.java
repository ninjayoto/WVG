import java.util.LinkedList;

public class Spider
{
    //all spiders for each queue run in parallel and each thread only stops when the previous thread stops
    public static LinkedList<String> links = new LinkedList<String>();
    public static LinkQueue[] linkQueue;
    public static LinkGetter[] lg;
    public static int count;
    public static int limit; //limit won't be capped exactly
    private static String url;
    private static int levels;

    public static int getLevels()
    {
        return levels;
    }

    public static void crawl(String aURL, int level, int numCap)
    {
        limit = numCap;
        levels = level;
        url = aURL;
        LinkGetter.setOrigURL(url);
        linkQueue = new LinkQueue[level + 1];
        for (int i = 0; i < linkQueue.length; i++)
        {
            linkQueue[i] = new LinkQueue();
        }
        linkQueue[0].enqueueLink(url);
        links.add(url);
        lg = new LinkGetter[levels + 1]; //original link is in its own queue
        lg[0] = new LinkGetter(0);
        Proxies.newProxy();
        lg[0].start();
        for (int i = 1; i <= levels; i++)
        {
            lg[i] = new LinkGetter(i);
            lg[i].start();
        }
        for (int i = 0; i <= levels; i ++)
        {
            try
            {
                lg[i].join();
            }
            catch (Exception e)
            {
                //System.out.println(e);
            }
        }
        Proxies.stopProxy();
        return;
    }
}
