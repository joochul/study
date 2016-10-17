import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class SampleTest {

	public static void main(String[] args){
    	SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");
//    	SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		
    	TimeZone tz = TimeZone.getTimeZone("Greenwich");
    	dayTime.setTimeZone(tz);
    	String str = dayTime.format(new Date(System.currentTimeMillis()));
    	
        System.out.println("[STANDARD DATE]"+str);
	}
	
}
