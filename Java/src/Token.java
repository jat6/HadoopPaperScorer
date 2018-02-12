// User defined type that works as a dictionary to store tokens and a numeric value
public class Token {
	
	String token;
	int count;
	double score;
	
	Token(String tok, int c) {
		token = tok;
		count = c;
	}
	
	Token(String tok, double d) {
		token = tok;
		score = d;
	}
	
	public String GetToken() {
		return this.token;
	}
	
	public int GetCount() {
		return this.count;
	}
}
