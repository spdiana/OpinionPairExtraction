package aspectator;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import aspectator.Pattern;
import aspectator.TypeDependency;

public class ParseTree {
	public static void  loadListPattern(int idReview, String review) {
		
		Annotation annotation = new Annotation(review);
		StanfordProperties.getPipeline().annotate(annotation);

		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			
			ArrayList<Pattern> mainPattern = new ArrayList<>();
			ArrayList<Pattern> extensionPattern = new ArrayList<>();
			List<SemanticGraphEdge> graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class).edgeListSorted();

			for (SemanticGraphEdge semanticGraph : graph) {
				String relation = semanticGraph.getRelation().toString();
				//System.out.println(relation);
				TypeDependency typeRelaction = TypeDependency.getEnum(relation);
				
				String source = semanticGraph.getSource().value();
				String sourcePostag = semanticGraph.getSource().tag();
				int sourceId = semanticGraph.getSource().backingLabel().index();

				String target =  semanticGraph.getTarget().value();
				String targetPostag = semanticGraph.getTarget().tag();
				int targetId = semanticGraph.getTarget().backingLabel().index();
				
				Pattern pattern = new Pattern(typeRelaction, source, sourcePostag, sourceId, target, targetPostag, targetId);
				//if(pattern.relation== TypeDependency.CONJ) {
				//	System.out.print("ID "+idReview+"\t"+relation);
				//	pattern.toString(); 
				//}
			
				if(TypeDependency.MAIN_PATTERN == pattern.isPattern(pattern)) {
					mainPattern.add(pattern);
				} else if(TypeDependency.EXTENSION_PATTERN == pattern.isPattern(pattern)) {
					extensionPattern.add(pattern);
				}
			}
			ExtractDependencyPath.extractDependency(idReview, mainPattern, extensionPattern);
		}
	}
}
