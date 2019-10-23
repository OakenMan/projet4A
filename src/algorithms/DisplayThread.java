package algorithms;

import com.mxgraph.view.mxGraph;

import controller.mainWindow.MainWindowController;
import view.mainWindow.MainWindow;

public class DisplayThread implements Runnable {

	MainWindow view;
	mxGraph graph;
	
//	public DisplayThread(MainWindow view, mxGraph graph) {
//		this.view = view;
//		this.graph = graph;
//	}
	
	@Override
	public void run() {
		MainWindowController.nextStep();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
