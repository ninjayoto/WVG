import java.util.concurrent.TimeUnit;

public class RandomPauser
{
    public static void pause()
    {
        int time = (int)(Math.random() * 7) + 2;
        try
        {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (Exception e)
        {
        }
    }
}
