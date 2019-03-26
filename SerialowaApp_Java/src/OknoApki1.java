import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/** Pierwsze okno programu, w którym u¿ytkownik jest witany 
 * @author Szymon
 *
 */
public class OknoApki1 {

	protected Shell shell;
	private PanelKontrolny panel;
	
	
	
	public static void main(String[] args) {
		try {
			OknoGlowne window = new OknoGlowne();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
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
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 261);
		shell.setText("Start");
		
		Button btnKontynuuj = new Button(shell, SWT.NONE);
		btnKontynuuj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				try {
					OknoGlowne window = new OknoGlowne();
					window.setPanel(panel);
					Czas czas = new Czas(panel.getSystem(), window);
					panel.getSystem().setCzas(czas);
					czas.start();
					window.open();
				} catch (Exception excep) {
					excep.printStackTrace();
				}
			}
		});
		btnKontynuuj.setBounds(34, 79, 368, 30);
		btnKontynuuj.setText("Kontynuuj");
		
		Button btnWyjdz = new Button(shell, SWT.NONE);
		btnWyjdz.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				System.exit(0);
			}
		});
		btnWyjdz.setText("Wyjd\u017A");
		btnWyjdz.setBounds(34, 140, 368, 30);
		
		Label lblWitaj = new Label(shell, SWT.NONE);
		lblWitaj.setBounds(33, 20, 368, 20);
		lblWitaj.setText("Witaj w aplikacji! Aby przej\u015B\u0107 dalej kliknij \"Kontynuuj\".");

	}
	
	public PanelKontrolny getPanel() {
		return panel;
	}

	public void setPanel(PanelKontrolny panel) {
		this.panel = panel;
	}

	
}
