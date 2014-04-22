package am.andranik.testtwitter;

import java.util.ArrayList;

import org.json.JSONObject;

public class TwittsAnaliser implements Runnable{
	ArrayList<JSONObject> twittsArray;
	
	public TwittsAnaliser(ArrayList<JSONObject> twittsArray){
		this.twittsArray = twittsArray;
		System.out.println();
	}

	public void run() {
		int retwitts = 0;
        int englishTwitts = 0;
        
        int twittsCount = twittsArray.size();
        
        System.out.println("Twitts Analysed --> " + twittsCount);
        
        for(JSONObject twitt : twittsArray){
        	if(twitt.has("text")){
        		if(twitt.getString("text").startsWith("RT")){
        			retwitts++;
        		}
        	}
        	
        	if(twitt.has("lang")){
	        	if(twitt.getString("lang").equals("en")){
	        		englishTwitts++;
	        	}
        	}
        }
        
        try{
        	int retwittPercent = (int) (retwitts * 100) / twittsCount;
        	int englisPercent = (int) (englishTwitts * 100) / twittsCount;
        	
        	System.out.println("Retwitts ---> " + retwittPercent + "%");
            System.out.println("English ---> " + englisPercent + "%");
        } catch (ArithmeticException e){
        	System.out.println("Ooops!");
        }
    	
        
	}
	
	

}
