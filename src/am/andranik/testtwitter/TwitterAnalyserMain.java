package am.andranik.testtwitter;

public class TwitterAnalyserMain {
    public static void main(String[] args){
//    	String trackWord = "twitter";
    	int limit = 500;
    	
//    	if(args.length > 0){
//    		trackWord = args[0];
//    	}
    
    	if(args.length > 0){
    		try{
    			limit = Integer.parseInt(args[0]);
    		} catch(NumberFormatException e){}
    	}

        TwitterStream stream = new TwitterStream(limit);
        new Thread(stream).start();
    }
}
