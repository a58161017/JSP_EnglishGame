package kiuno.utility;

public class TextProcessor {
	public String checkNull(Object obj, String defaultValue){
		return obj!=null?(String)obj:defaultValue;
	}
}
