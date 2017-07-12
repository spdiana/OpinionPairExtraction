package aspectator;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class StanfordProperties {

	private static StanfordCoreNLP pipeline;

	private static StanfordCoreNLP loadProperties(boolean depparse) {
		Properties props = new Properties();
		if(depparse) {
			props.put("annotators", "tokenize, ssplit, pos, lemma, depparse");
			//props.put("annotators", "tokenize, ssplit, pos, lemma, ner, depparse", "parse","sentiment, dcoref");
			props.put("depparse.extradependencies", "MAXIMAL");
			props.put("depparse.originalDependencies", false);
		} else {
			props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
			props.put("parse.originalDependencies", true);
		}
		pipeline = new StanfordCoreNLP(props);
		return pipeline;
	}

	public static StanfordCoreNLP init(boolean depparse) {
		if (pipeline == null ) {
			pipeline = loadProperties(depparse);
		}
		return pipeline;
	}

	public static StanfordCoreNLP getPipeline() {
		return pipeline;
	}
}
