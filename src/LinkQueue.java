import java.util.LinkedList;

public class LinkQueue
{
    private LinkedList<String> links = new LinkedList<String>();
    
    public void enqueueLink(String url)
    {
        links.add(url);
        return;
    }
    
    public String dequeueLink()
    {
        return links.remove(0);
    }
    
    public int getLength()
    {
        return links.size();
    }
}
