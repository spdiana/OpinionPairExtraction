package aspectator;

public class OpinionPair {

	int idReview;
	String compound;
	String head;
	String headPostag;
	String neg;
	String advMod;
	String modifier;
	String modifierPostag;
	String relation;

	public OpinionPair() {

	}

	public OpinionPair(int idReview, String compound, String head, String headPostag, String neg, String advMod,
			String modifier, String modifierPostag, String relation) {
		super();
		this.idReview = idReview;
		this.compound = compound;
		this.head = head;
		this.headPostag = headPostag;
		this.neg = neg;
		this.advMod = advMod;
		this.modifier = modifier;
		this.modifierPostag = modifierPostag;
		this.relation = relation;
	}

}
