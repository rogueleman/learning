package com.leman.utils.maybe;

public class StopWatch {

	private final static String ANSWER_START = "elapsedTime ";
	
	private long start;
    private long stop;

    public void start() {
        start = System.currentTimeMillis(); // start timing
    }

    public void stop() {
        stop = System.currentTimeMillis(); // stop timing
    }

    public String toString() {
    	String answerUM = ""; 
    	long executionTime = elapsedTimeMillis();
    	

    	if (executionTime < 1000) {
    		answerUM = " miliSeconds";
    	} else {
    		executionTime = elapsedTimeSeconds();
    		if (executionTime < 60) {
    			answerUM = " seconds";
    		} else {
    			executionTime = elapsedTimeMinutes();
    			if (executionTime < 60) {
        			answerUM = " minutes";
    			} else {
        			executionTime = elapsedTimeHours();
        			if (executionTime < 60) {
            			answerUM = " hours";
        			}
        		}
    		}
    	}

    	return  ANSWER_START + Long.toString(executionTime) + answerUM; // print execution time
    }

    private long elapsedTimeMillis() {
    	return stop - start;
    }

    private long elapsedTimeSeconds() {
        return elapsedTimeMillis()/1000;
    }

    private long elapsedTimeMinutes() {
        return elapsedTimeSeconds()/60;
    }

    private long elapsedTimeHours() {
        return elapsedTimeMinutes()/60;
    }
    
}
