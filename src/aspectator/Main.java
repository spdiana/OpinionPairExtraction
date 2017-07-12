package aspectator;


public class Main {


	public static void main(String[] args) {
		StanfordProperties.init(false);

		String test =  "Nokia has fine, excellent, cheapest battery.";
		ParseTree.loadListPattern(1, test);
	}
}
