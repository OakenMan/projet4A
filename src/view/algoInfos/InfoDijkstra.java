package view.algoInfos;

public class InfoDijkstra extends AbstractAlgoInfos {

	public InfoDijkstra() {
		super();
		
		append("Caract�ristiques :\n");
		append("- Graphes quelconques\n");
		append("- Longueurs positives\n");
		append("- One to All\n");
		append("- Complexit� : O(a + n.log(n))");
	}
}
