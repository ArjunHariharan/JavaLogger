package log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;


public class Log 
{		
	public static Logger masterlog = Logger.getLogger("LogProj");	
	
	public static void main(String[] args) 
	{
		Log obj = new Log();
		masterlog.debug("Calling Monitor Function from main");
		obj.Monitor();
	}
	
	public void Monitor()   // parent function which monitors all the child threads for each pool
	{
		threadclass grp = null;
		Thread t=null;
		int i=0;
		
		masterlog.debug("Entered Monitor Function from main");
		
		{ 			
							
			while(i<3)  // for every group, do the following.
			{
				masterlog.debug("Creating New thread for group- " + i);
				grp = new threadclass(i);
				t = new Thread(grp);
				t.start();
				masterlog.debug("Started New thread for group- " + i);
				i++;
			}
		} 
	}
}	

class threadclass implements Runnable  // a thread for each group
{
	
	 Logger log;
	 int Group;
	 printdata p;
	
	public threadclass(int GroupId)  // contructor
	{	
		Group = GroupId;
	}
	
	@Override
	public void run()
	{			    
		
		String parent = "LogProj", name = "Group";
		name = name + Group; 
		String loggerName = parent + "." + name;
        Logger log = Logger.getLogger(loggerName);


        //Create Logging File Appender
        RollingFileAppender fileApp = new RollingFileAppender();
        fileApp.setName("LogProj." + loggerName + "_FileAppender");
        fileApp.setFile("c:\\java\\"+ name+".log");
        fileApp.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss}  (%0F:%L) %3x - %m%n"));
        fileApp.setThreshold(Level.toLevel("debug"));
        fileApp.setAppend(true);
        fileApp.activateOptions();

        log.addAppender(fileApp);
        
		p = new printdata(log);
		
		for(int i=10;i<20;i++)
			log.debug("Group-"+ Group + " test no- " + i);
		
		p.print();
		
		log.removeAllAppenders();
	}		
}

class printdata
{
	Logger Log;
	int GroupId;
	
	public printdata(Logger l)  //constructor 
	{
		Log = l;
	}
	
	public void print()
	{
		for(int i=0;i<3;i++)
		{
			Log.debug("Printing data " + i +" of thread-" + GroupId);
		}
	}
}