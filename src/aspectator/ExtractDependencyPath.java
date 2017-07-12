package aspectator;

import java.util.ArrayList;

import aspectator.Pattern;
import aspectator.TypeDependency;

public class ExtractDependencyPath {

	public static void extractDependency(int idReview, ArrayList<Pattern> listPattern, ArrayList<Pattern> extensionPattern) {

		for (Pattern pattern : listPattern) {	
			switch (pattern.relation) {
			case AMOD:
				if (!patternAmodNSubj(idReview, pattern, listPattern, extensionPattern)){
					patternAmod(idReview, pattern, listPattern, extensionPattern);
				}
				break;
			case DOBJ:
				patternNsubjDobj(idReview, pattern, listPattern, extensionPattern);
				patternDobj2(idReview, pattern, extensionPattern);
				break;
			case XCOMP:
				patterNsubjXComp(idReview, pattern, listPattern, extensionPattern);
				break;
			case COP:
				pattNsubjCop(idReview, pattern, listPattern, extensionPattern);
				break;
			case NSUBJPASS:
				patternnNSubjpassAdvmod(idReview, pattern, extensionPattern);
				break;
			case NMOD:
				patternNmod(idReview, pattern,listPattern, extensionPattern);
				break;	
			case NSUBJ:
				patternNSubjConj(idReview, pattern, extensionPattern, extensionPattern);
				break;	

			}
		}
	}

	public static String[] extentionPattern(ArrayList<Pattern> ext, int id1, int id2, int id3) {
		String r[] = {"","",""};
		for (Pattern extention : ext) {

			if (extention.relation == TypeDependency.COMPOUND && extention.sourceId == id1){
				r[0] = extention.target;
			} else if (extention.relation == TypeDependency.NEG && extention.sourceId == id2) {
				r[1] = extention.target;
			} else if (extention.relation == TypeDependency.NEG && extention.sourceId == id1) {
				r[1] = extention.target;
			} else  if (extention.relation == TypeDependency.ADVMOD && extention.sourceId == id3){
				r[2] = extention.target;
			} 
		}
		return r;
	}

	private static void patterXCompAdvmod(int idReview, Pattern xcomp, ArrayList<Pattern> listPattern,
			ArrayList<Pattern> extensionPattern) {
		for (Pattern advmod : extensionPattern) {
			if (advmod.relation == TypeDependency.ADVMOD && xcomp.targetId == advmod.sourceId
					&& advmod.sourcePostag.startsWith("JJ") && xcomp.sourcePostag.startsWith("VB")) {
				saveBD(idReview, xcomp.source,xcomp.sourcePostag, "", advmod.target +" "+advmod.source, advmod.sourcePostag, "xcomp-advmod");
			}
		}	
	}


	public static void patternAmod(int idReview, Pattern amod, ArrayList<Pattern> pattern,  ArrayList<Pattern> ext) {
		String r[] = extentionPattern(ext, amod.sourceId, amod.sourceId, amod.targetId);
		String deps = "";
		for (Pattern dep : pattern) {
			if(dep.relation == TypeDependency.DEP && amod.targetId == dep.sourceId){
				deps = dep.target;
			}
		}
		saveBD(idReview, r[0]+" "+amod.source,amod.sourcePostag, r[1], r[2]+" "+amod.target +" "+deps, amod.targetPostag, "amod");

		for (Pattern conj : ext) {
			if (conj.relation == TypeDependency.CONJ && conj.sourceId == amod.sourceId && (conj.targetPostag.startsWith("NN")) ) {			
				String r2[] = extentionPattern(ext, amod.sourceId, conj.targetId, amod.sourceId); 
				saveBD(idReview, r2[0]+" "+conj.target, conj.targetPostag, r2[1], r2[2]+" "+amod.target, amod.targetPostag, "amod-conj");
			}			
		}
	}

	public static boolean patternAmodNSubj(int idReview, Pattern amod, ArrayList<Pattern> pattern, ArrayList<Pattern> ext) {
		boolean save = false;
		String advmod = "";
		for (Pattern nsubj : pattern) {
			if (nsubj.relation == TypeDependency.NSUBJ &&  amod.sourceId == nsubj.targetId) {
				for (Pattern extention : ext) {
					if (extention.relation == TypeDependency.ADVMOD && extention.targetPostag.startsWith("RB")
							&& nsubj.sourceId == extention.sourceId){
						advmod = extention.target;
					}
				}
				saveBD(idReview,amod.target +" "+ amod.source, amod.targetPostag  +" "+ amod.sourcePostag, 
						"", advmod +" " + nsubj.source, nsubj.sourcePostag, "amod-nsubj");
				save = true;
			}	
		}
		return save;
	}

	public static void patternNsubjDobj(int idReview, Pattern dobj, ArrayList<Pattern> pattern, ArrayList<Pattern> ext) {
		for (Pattern nsubj : pattern) {
			if (nsubj.relation == TypeDependency.NSUBJ &&  dobj.sourceId == nsubj.sourceId
					&& nsubj.targetPostag.startsWith("NN") && (dobj.targetPostag.startsWith("VB") || dobj.targetPostag.startsWith("JJ"))) {
				String r[] = extentionPattern(ext, nsubj.targetId, dobj.sourceId, dobj.targetId); 
				saveBD(idReview, r[0]+" "+nsubj.target, nsubj.targetPostag, r[1], r[2]+" "+dobj.target, dobj.targetPostag, "nsubj-dobj");
			}	
		}
	}


	public static void patterNsubjXComp(int idReview, Pattern xcomp, ArrayList<Pattern> pattern, ArrayList<Pattern> ext) {
		for (Pattern nsubj : pattern) {
			if (nsubj.relation == TypeDependency.NSUBJ && xcomp.sourceId == nsubj.sourceId) {
				if(nsubj.targetPostag.startsWith("NN")) {
					String r[] = extentionPattern(ext, nsubj.targetId, xcomp.sourceId, xcomp.targetId); 
					saveBD(idReview, r[0]+" "+nsubj.target,nsubj.targetPostag, r[1], r[2]+" "+xcomp.target, xcomp.targetPostag, "nsubj-xcomp-NN");
				} else if(nsubj.targetPostag.startsWith("PRP") && xcomp.sourcePostag.startsWith("VB")&& 
						(xcomp.targetPostag.equals("VBN") || xcomp.targetPostag.startsWith("JJ"))) {
					String r[] = extentionPattern(ext, nsubj.targetId, xcomp.sourceId, xcomp.targetId); 
					saveBD(idReview, r[0]+" "+xcomp.source,xcomp.sourcePostag, r[1], r[2]+" "+xcomp.target, xcomp.targetPostag, "nsubj-xcomp-VB");
				}
			}
		}	
	}



	public static void pattNsubjCop(int idReview, Pattern cop, ArrayList<Pattern> pattern, ArrayList<Pattern> ext) {
		for (Pattern nsubj : pattern) {
			if (nsubj.relation == TypeDependency.NSUBJ && cop.sourceId == nsubj.sourceId
					&& (nsubj.targetPostag.startsWith("NN")) )	 {
				String r[] = extentionPattern(ext, nsubj.targetId, cop.sourceId, cop.sourceId);
				saveBD(idReview, r[0]+" "+nsubj.target,nsubj.targetPostag, r[1], r[2]+" "+cop.source, cop.sourcePostag, "nsubj-cop");

				for (Pattern conj : ext) {
					if (conj.relation == TypeDependency.CONJ && conj.sourceId == cop.sourceId 
							&& (conj.targetPostag.startsWith("JJ"))) {
						String r2[] = extentionPattern(ext, conj.targetId, conj.targetId, conj.targetId);
						saveBD(idReview, r2[0]+" "+nsubj.target, nsubj.targetPostag, r2[1], r2[2]+" "+conj.target, conj.targetPostag, "nsubj-cop-conj");
					}
				}
			}	
		}
	}

	public static void patternnNSubjpassAdvmod(int idReview, Pattern nsubjpass,  ArrayList<Pattern> ext) {
		Pattern adv = null;
		for (Pattern extension : ext) {
			if (extension.relation == TypeDependency.ADVMOD && extension.sourceId == nsubjpass.sourceId) {
				adv = extension;
				String r[] = extentionPattern(ext, nsubjpass.targetId, adv.sourceId, 0);

				saveBD(idReview, r[0]+" "+nsubjpass.target,nsubjpass.targetPostag, r[1], adv.target+" "+nsubjpass.source, nsubjpass.sourcePostag, "nsubjpass-advmod");
			}	
		}
	}


	public static void patternNmod(int idReview, Pattern nMod, ArrayList<Pattern> pattern, ArrayList<Pattern> ext) {
		String r[] = extentionPattern(ext, nMod.targetId, nMod.sourceId, nMod.sourceId);
		for (Pattern nsubj : pattern) {
			if (nsubj.relation == TypeDependency.NSUBJ && nMod.sourceId == nsubj.targetId &&
					(nsubj.sourcePostag.startsWith("JJ") || nsubj.sourcePostag.equals("VBN"))) {
				saveBD(idReview,r[0]+" "+nMod.target, nMod.targetPostag,  r[1], r[2]+" "+nsubj.source,nsubj.sourcePostag, "nmod");
			}
		}
	}


	public static void patternNSubjConj(int idReview, Pattern nSubj, ArrayList<Pattern> exPattern, ArrayList<Pattern> ext) {
		String rr[] = extentionPattern(ext, 0, nSubj.sourceId, nSubj.sourceId);
		if(nSubj.sourcePostag.startsWith("VB") && nSubj.targetPostag.startsWith("NN")){
			saveBD(idReview, nSubj.target, nSubj.targetPostag, rr[1],rr[2] +" "+nSubj.source, nSubj.sourcePostag, "nsubj");
		}


		for (Pattern conj : exPattern) {
			if (conj.relation == TypeDependency.CONJ && conj.sourceId  == nSubj.sourceId
					&& (conj.targetPostag.startsWith("JJ")&& nSubj.targetPostag.startsWith("NN"))) {
				String r[] = extentionPattern(ext, conj.targetId, nSubj.sourceId, nSubj.targetId);
				saveBD(idReview, r[0]+" "+nSubj.target, nSubj.targetPostag, r[1], r[2]+" "+conj.target, conj.targetPostag, "nsubj-conj");
			}	
		}
	}


	public static void patternDep(int idReview, Pattern dep,  ArrayList<Pattern> pattern) {
		for (Pattern dep2 : pattern) {
			if(dep.sourcePostag.startsWith("NN") && dep.targetPostag.startsWith("NN") && dep.sourceId == dep2.sourceId 
					&& dep2.targetPostag.startsWith("JJ")) {
				saveBD(idReview,  dep.target +" "+ dep.source ,  dep.sourcePostag +" "+ dep.targetPostag,"", dep2.target ,dep2.targetPostag, "dep");
			}
		}
	}


	public static void patternDepCompound(int idReview, Pattern dep,  ArrayList<Pattern> ext) {

		for (Pattern extension : ext) {
			if (extension.relation == TypeDependency.COMPOUND && dep.targetId == extension.sourceId){
				saveBD(idReview, extension.target +" "+ extension.source,  extension.sourcePostag +" "+ extension.targetPostag,"", dep.source ,dep.sourcePostag, "dep-compound");
			}
		}
	}


	public static void patternDobj2(int idReview, Pattern dobj2,  ArrayList<Pattern> ext) {
		String r[] = extentionPattern(ext, dobj2.targetId, dobj2.sourceId, dobj2.sourceId);
		if(!dobj2.sourcePostag.equals("VB") && !dobj2.sourcePostag.equals("VBG")){
			saveBD(idReview,r[0]+" "+dobj2.target,  dobj2.targetPostag,r[1], r[2]+ " "+dobj2.source, dobj2.sourcePostag, "dobj2");
		}
	}


	private static void saveBD(int  idReview, String h, String postagH, String neg, String m, String postagM,  String relation) {


	}

}
