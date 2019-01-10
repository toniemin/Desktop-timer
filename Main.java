
import java.io.File;


/**
 * Made by:

 * Topi Nieminen
 * budy426@gmail.com
 * 
 */

public class Main {

    public static void main(String[] args)
    {
        if ( args.length < 3 )
        {
            System.out.println("No arguments given.");
            System.exit(1);
        }
       
        Timer timer = new Timer(System.out, new File("wahoo.waw"));
        
        System.out.println("Setting timer for: "+
            args[0] + " hours "+
            args[1] + " minutes "+
            args[2] + " seconds.");
        try
        {
            timer.setAlarm(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]));
            
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
