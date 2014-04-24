package am.andranik.testtwitter;

import java.util.ArrayList;

import org.json.JSONObject;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

public class TwittsAnaliser implements Runnable{
	ArrayList<JSONObject> twittsArray;
	
	public TwittsAnaliser(ArrayList<JSONObject> twittsArray){
		this.twittsArray = twittsArray;
		System.out.println();
	}

	public void run() {
		int retwitts = 0;
        int englishTwitts = 0;
        int localEnglishTwitts = 0;
        
        int twittsCount = twittsArray.size();
        
        Detector detector = null;

        System.out.println("Twitts Analysed --> " + twittsCount);
        
        for(JSONObject twitt : twittsArray){
        	if(twitt.has("text")){
        		String text = twitt.getString("text");
        		if(text.startsWith("RT")){
        			retwitts++;
        		}
        		
        		try {
        			detector = DetectorFactory.create();
        			detector.append(text);
        			
        			if(detector.detect().equals("en")){
        				localEnglishTwitts++;
        			}
        		} catch (LangDetectException e1) {
        			//e1.printStackTrace();
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
        	int localEnglisPercent = (int) (localEnglishTwitts * 100) / twittsCount;
        	
        	System.out.println("Retwitts ---> " + retwittPercent + "%");
            System.out.println("Twitter Detected English ---> " + englisPercent + "%");
            System.out.println("Local Detected English ---> " + localEnglisPercent + "%");

        } catch (ArithmeticException e){
        	System.out.println("Ooops!");
        }
    	
        
	}
	
	

}
