import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LinkGetter extends Thread
{
    //even if linkgetter finishes, it will keep waiting till the previous linkgetter stops
    //every link it gets must not be out of domain, duplicates are not okay 
    private int queueNumber;
    private boolean allowedToAdd;
    private static String origURL;

    public LinkGetter(int queueNumber)
    {
        this.queueNumber = queueNumber;
        if (this.queueNumber == Spider.getLevels())
        {
            allowedToAdd = false; //add to next queue
        }
        else
        {
            allowedToAdd = true;
        }
    }

    public static void setOrigURL(String url)
    {
        origURL = url;
    }

    public void run()
    {
        if (queueNumber == 0)
        {
            extractLinks(Spider.linkQueue[0].dequeueLink());
        }
        else
        {
            while(Spider.lg[queueNumber - 1].isAlive())
            {
                for (int i = 0; i < Spider.linkQueue[queueNumber].getLength(); i++)
                {
                    if (Spider.count <= Spider.limit)
                    {
                        extractLinks(Spider.linkQueue[queueNumber].dequeueLink());
                    }
                    else
                    {
                        break;
                    }
                }
            }
            //System.out.println("THREAD " + queueNumber + " ENDED.");
        }
    }

    private void extractLinks(String url)
    {
        try
        {
            //the following line disables SSL verification
            HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
            URL link = new URL(url);
            URLConnection connection = link.openConnection();
            connection.setRequestProperty("User-Agent", UserAgent.giveAgent());
            InputStream stream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            String html = "";
            while ((line = br.readLine()) != null) 
            {
                html += line;
            }
            //System.out.println(html); //for testing
            //parsing with regex and some common cases
            //certain links will provide bad results
            Pattern linkPattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href+=[\"']([^\"']*)['\"]",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
            Matcher matches = linkPattern.matcher(html);
            String temp;
            while(matches.find())
            {
                temp = matches.group();
                if ((temp.substring(0, 9).equalsIgnoreCase("<a href=\"") || temp.substring(0, 9).equalsIgnoreCase("<a href='")) && (temp.indexOf(origURL.substring(5)) >= 0 || temp.indexOf(origURL.substring(6)) >= 0))
                {
                    temp = temp.substring(9, temp.length() - 1);
                    if (temp.indexOf("http") < 0  && origURL.indexOf("https") < 0)
                    {
                        temp = "http:" + temp;
                    }
                    if (temp.indexOf("http") < 0  && origURL.indexOf("https") >= 0)
                    {
                        temp = "https:" + temp;
                    }
                    if (!Spider.links.contains(temp)) //need a more efficient method here, will be fixed later on
                    {
                        Spider.count++;
                        if (allowedToAdd)
                        {
                            Spider.linkQueue[queueNumber + 1].enqueueLink(temp);
                        }
                        Spider.links.add(temp);
                        //System.out.println("Queue: " + queueNumber + " " + Spider.count + " " + temp);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
