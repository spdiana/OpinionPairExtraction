package aspectator;


public enum TypeDependency {
	AMOD(1),
	DOBJ(2),
	XCOMP(3),
	COP(4),
	NSUBJ(5),
	ACOMP(6),
	NSUBJPASS(7),
	NMOD(8),
	DEP(9),
	CCOMP(10),

	COMPOUND(14),
	ADVMOD(15),
	NEG(16),
	CONJ(17),
	

	MAIN_PATTERN(18),
	EXTENSION_PATTERN(19),
	NONE(20);

	private final int value;

	private TypeDependency(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static TypeDependency getEnum(String wordRelation) {
		TypeDependency type = TypeDependency.NONE;
		if(wordRelation.startsWith("nmod")) {
			wordRelation ="nmod";
		}
		if(wordRelation.startsWith("conj:and")) {
			wordRelation ="conj";
		}
		for (TypeDependency typeDependency : TypeDependency.values()) {
			if (typeDependency.name().equalsIgnoreCase(wordRelation))
				type =  typeDependency;
		}
		return type;
	}
}
