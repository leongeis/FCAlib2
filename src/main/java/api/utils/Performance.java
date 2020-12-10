package api.utils;

/**
 * TODO Interface Description, Additional Methods
 * Utility Interface, which can be used to set
 * a time stamp, get the execution time of methods,
 * ...
 * @author Leon Geis
 */
public interface Performance {

    static long setTimeStamp(){
        return System.nanoTime();
    }

    static long getExecutionTime(long firstTimeStamp, long secondTimeStamp){
        //First check if firstTimeStamp is greater or equal than secondTimeStamp
        if(firstTimeStamp>=secondTimeStamp){
            //If it is return 0
            return 0;
        }
        else{
            //Return Execution Time
            return secondTimeStamp-firstTimeStamp;
        }
    }

    static String getTimeInFormat(long elapsedTime){
        //Return String of Format Minutes:Seconds:Milliseconds
        //TODO
        return null;
    }




}
