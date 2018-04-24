import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.lang.Thread;

public class Bot extends Thread
{
    private final int TIMES;
    private final String LINK;
    private String[] agents;
    private String[] referers;

    public Bot (int y, String z, String uA, String ref) //check nulls
    {
        Referers.initialize();
        TIMES = y;
        LINK = z;
        loadUserAgents();
        loadReferers();
    }

    private void loadUserAgents()
    {
        agents = new String[TIMES];
        for (int i = 0; i <= (agents.length - 1); i++)
        {
            agents[i] = UserAgent.giveAgent();
        }
    }

    private void loadReferers()
    {
        referers = new String[TIMES];
        referers = Referers.referer(TIMES);
    }

    public void run()
    {
        startbot();
    }

    private void startbot()
    {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        for (int i = 0; i < TIMES; i++)
        {
            newProxy();
            try
            {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(LINK);
                request.addHeader("User-Agent", agents[i]);
                request.addHeader("Referer",referers[i]);
                HttpResponse response = client.execute(request);
                ViewGenerator.progress += 1;
                //System.out.println("\nSending 'GET' request to URL : " + LINK); //these two lines are for testing
                //System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
                RandomPauser.pause();
                client.getConnectionManager().shutdown();
            }
            catch (Exception e)
            {
            }
            System.getProperties().put( "proxySet", "false" );
            Proxies.stopProxy();
        }
    }

    private void newProxy()
    {
        Proxies.newProxy();
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
        System.getProperties().put( "socksProxyPort", "9050" );
        System.setProperty("socksProxyVersion" , "5");
    }
}
