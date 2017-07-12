package aspectator;

public class Pattern {

	public String source;
	public String sourcePostag;
	public int sourceId;
	public String target;
	public String targetPostag;
	public int targetId;
	public TypeDependency relation;

	public Pattern(TypeDependency relation, String source, String sourcePostag, int sourceId, String target, String targetPostag, int targetId) {
		this.source = source;
		this.sourcePostag = sourcePostag;
		this.sourceId = sourceId;
		this.target = target;
		this.targetPostag = targetPostag;
		this.targetId = targetId;
		this.relation = relation;
	}


	public TypeDependency isPattern(Pattern pattern) {
		TypeDependency typePattern = null;
		switch (pattern.relation) {
		case AMOD:
			if  (pattern.sourcePostag.startsWith("NN") && pattern.targetPostag.startsWith("JJ") || pattern.targetPostag.startsWith("VB")){
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;
		case DOBJ:
			if ((pattern.sourcePostag.startsWith("VBD") || pattern.sourcePostag.startsWith("VBN") || pattern.sourcePostag.startsWith("JJ")) && pattern.targetPostag.startsWith("JJ")
					|| pattern.targetPostag.startsWith("NN")) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;
		case XCOMP:
			if (pattern.sourcePostag.startsWith("VB") && pattern.targetPostag.startsWith("JJ")|| pattern.targetPostag.startsWith("VB")) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;	
		case COP:
			if (pattern.sourcePostag.startsWith("JJ") && pattern.targetPostag.startsWith("VB")) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;
		case NSUBJ:
			if ((pattern.sourcePostag.startsWith("VB") || pattern.sourcePostag.startsWith("JJ")) && (pattern.targetPostag.startsWith("NN") || pattern.targetPostag.equals("PRP"))) {
			//if ((pattern.sourcePostag.startsWith("VB") || pattern.sourcePostag.startsWith("JJ")) && (pattern.targetPostag.startsWith("NN") )) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
		case NSUBJPASS:
			if ((pattern.sourcePostag.startsWith("VB") || pattern.sourcePostag.startsWith("JJ")) && (pattern.targetPostag.startsWith("NN"))) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;
		case NMOD:
			if ( pattern.targetPostag.startsWith("NN")) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;
			
		case DEP:
			if (pattern.sourcePostag.startsWith("NN")  && pattern.targetPostag.startsWith("NN") || pattern.targetPostag.startsWith("JJ")) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;
		case CCOMP:
			if (pattern.sourcePostag.startsWith("VB")  && pattern.targetPostag.startsWith("VB") || pattern.targetPostag.startsWith("JJ")) {
				typePattern = TypeDependency.MAIN_PATTERN;
			}
			break;
		case COMPOUND:
			if (pattern.sourcePostag.startsWith("NN") && pattern.targetPostag.startsWith("NN")) {
				typePattern = TypeDependency.EXTENSION_PATTERN;
			}
			break;
		case ADVMOD:
			if (pattern.sourcePostag.startsWith("VB") || pattern.sourcePostag.startsWith("JJ") && pattern.targetPostag.startsWith("RB")) {
				typePattern = TypeDependency.EXTENSION_PATTERN;
			}	
		case NEG:
			typePattern = TypeDependency.EXTENSION_PATTERN;
			break;
		case CONJ:
			if (pattern.sourcePostag.startsWith("NN")  || pattern.sourcePostag.startsWith("JJ") || pattern.sourcePostag.startsWith("VB")&& pattern.targetPostag.startsWith("JJ")) {
				typePattern = TypeDependency.EXTENSION_PATTERN;
			}
			break;
		}
		return typePattern;
	}



	@Override
	public String toString() {
		String p = "\t"+sourceId+"\t"+source +"\t"+sourcePostag+"\t"+targetId
				+"\t"+target+"\t"+targetPostag+"\t"+relation.toString();
		System.out.println(p);
		return p;
	}
}
