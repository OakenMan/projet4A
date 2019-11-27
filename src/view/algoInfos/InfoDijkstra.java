package view.algoInfos;

public class InfoDijkstra extends AbstractAlgoInfos {

	public InfoDijkstra() {
		super();
		
		append("Caractéristiques :\n");
		append("- Graphes quelconques\n");
		append("- Longueurs positives\n");
		append("- One to All\n");
		append("- Complexité : O(a + n.log(n))");
	}
}
