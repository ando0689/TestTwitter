package am.andranik.testtwitter;

import java.io.File;
import java.net.URISyntaxException;

import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

public class TwitterAnalyserMain {
    public static void main(String[] args) throws URISyntaxException{
    	String path;
    	int limit = 500;

    	if(args.length > 0){
    		path = args[0];
    	} else {
    		System.out.println("You must specify the path to language profiles!");
    		return;
    	}
    	
    	if(args.length > 1){
    		try{
    			limit = Integer.parseInt(args[1]);
    		} catch(NumberFormatException e){}
    	}

    	try {
			File profileDirectory = new File(path);
			DetectorFactory.loadProfile(profileDirectory);
 		} catch (LangDetectException e1) {
 			//e1.printStackTrace();
 			System.out.println("Cannot find any language profile in " + path);
 			return;
 		}
    	
        TwitterStream stream = new TwitterStream(limit);
        new Thread(stream).start();
    }
}
