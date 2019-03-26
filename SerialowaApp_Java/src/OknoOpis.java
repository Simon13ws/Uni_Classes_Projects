import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

/**Okno z opisem produktu
 * @author Szymon
 *
 */
public class OknoOpis {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 *//*
	public static void main(String[] args) {
		try {
			OknoOpis window = new OknoOpis();
			window.open(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * Open the window.
	 */
	public void open(Produkt p) {
		Display display = Display.getDefault();
		createContents(p);
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
	protected void createContents(Produkt p) {
		shell = new Shell();
		shell.setSize(322, 228);
		shell.setText("Opis");
		
		Label lblOpis = new Label(shell, SWT.WRAP);
		lblOpis.setBounds(10, 10, 284, 161);
		lblOpis.setText(p.getOpis());

	}
}
