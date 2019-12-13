package view.algoInfos;

public class InfoDijkstra extends AbstractAlgoInfos {

	public String getText() {
		return 
				("Caractéristiques :\n" +
						"- Graphes quelconques\n" +
						"- Longueurs positives\n" +
						"- One to All\n" +
						"- Complexité : O(a + n.log(n))");
	}
}
