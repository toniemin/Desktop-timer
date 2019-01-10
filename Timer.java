
/**
 * Made by:

 * Topi Nieminen
 * budy426@gmail.com
 * 
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import java.util.concurrent.TimeUnit;

public class Timer 
{

    /*
     * Attributes.
     * 
     */
    
    private PrintStream out;
    private Clip alarmSound;
    
    /*
     * Constructors.
     * 
     */
    
    public Timer(PrintStream output, File soundfile)
        throws IllegalArgumentException
    {
        try
        {
            this.out = output;
            
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundfile);
            alarmSound = AudioSystem.getClip();
            alarmSound.open(audioIn);
        }
        catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e )
        {
            throw new IllegalArgumentException();
        }
    }
    
    /*
     * Methods.
     * 
     */
    
    private void printTime(long time)
    {
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        long mins = TimeUnit.MILLISECONDS.toMinutes(time - TimeUnit.HOURS.toMillis(hours));
        long secs = TimeUnit.MILLISECONDS.toSeconds(
            time 
            - TimeUnit.HOURS.toMillis(hours) 
            - TimeUnit.MINUTES.toMillis(mins));
        
        if ( hours > 0 )
            out.print(hours + " h ");
        if ( mins> 0 )
            out.print(mins + " min ");
        out.print(secs + " s\n");
    }
    
    public void setAlarm(int seconds)
        throws InterruptedException
    {
        setAlarm(0, 0, seconds);
    }
    
    public void setAlarm(int minutes, int seconds)
        throws InterruptedException
    {
        setAlarm( 0, minutes, seconds );
    }
    
    public void setAlarm(int hours, int minutes, int seconds)
        throws InterruptedException
    {
        long start = System.currentTimeMillis();
        long end = start + ( (hours*3600) + (minutes*60) + (seconds) ) * 1000;
        
        out.println("Time remaining:");
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                int h = hours, min = minutes, sec = seconds;
                long time = System.currentTimeMillis();
                
                while ( time < end )
                {
                    printTime(end - time);
                    
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ex)
                    {
                        Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    sec--;
                    if ( sec <= 0 )
                    {
                        sec = 59;
                        if ( min == 0 )
                        {
                            min = 59;
                            if ( h > 0 )
                                h--;
                        }
                        else
                            min--;
                    }

                    time = System.currentTimeMillis();
                    
                    if ( time > end )
                        return;
                }
            }
        })
        .run();
            
        
        out.println("It is time!");
        alarmSound.start();
        Thread.sleep(5000);
    }
    
}
