package tests;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

public class CellWithLabel extends mxCell {

	/*===== ATTRIBUTES =====*/
	private String potentiel;
	
	/*===== BUILDER =====*/
	public CellWithLabel(Object value, mxGeometry geometry, String style, String potentiel)
	{
		super(value, geometry, style);
		setPotentiel(potentiel);
	}
	
	/*===== GETTERS AND SETTERS =====*/
	public String getPotentiel() {
		return potentiel;
	}
	
	public void setPotentiel(String potentiel) {
		this.potentiel = potentiel;
	}
}
