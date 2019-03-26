

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;

/** Okno z wykresem ogl¹dalnoœci dla danego produktu - liczba wyœwietleñ w danym tygodniu istnienia produktu
 * @author Szymon
 *
 */
public class OknoWykres {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		try {
			OknoWykres window = new OknoWykres();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * Open the window.
	 */
	public void open(Produkt p) {

		double[] tygodnie = new double[p.getOgladalnosc().size()];
		double[] ogladalnosc = new double[p.getOgladalnosc().size()];
		int ile = 0;
		int j = 0;
		for (Integer i : p.getOgladalnosc()) {
			ogladalnosc[j] = i - ile;
			ile = i;
			tygodnie[j] = j + 1;
			j++;
		}

		
		
		Display display = Display.getDefault();
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout());

		Chart chart = new Chart(shell, SWT.NONE);

		chart.getTitle().setText("Wyœwietlenia w czasie");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Tydzieñ od wypuszczenia");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Wyœwietlenia");

		// create scatter series
		ILineSeries scatterSeries = (ILineSeries) chart.getSeriesSet().createSeries(SeriesType.LINE, "Ogl¹dalnoœæ w czasie");
		//scatterSeries.setLineStyle(LineStyle.NONE);
		scatterSeries.setXSeries(tygodnie);
		scatterSeries.setYSeries(ogladalnosc);

		chart.getAxisSet().adjustRange();

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(double[] tygodnie, double[] ogladalnosc) {

	}

}
