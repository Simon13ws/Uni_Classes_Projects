import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


/** Okno do zmiany ceny produktu
 * @author Szymon
 *
 */
public class OknoZmienCene {

	protected Shell shell;
	private Text textNowa;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		try {
			OknoZmienCene window = new OknoZmienCene();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * Open the window.
	 */
	public void open(Obiekt obiekt) {
		Display display = Display.getDefault();
		createContents(obiekt);
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
	protected void createContents(Obiekt obiekt) {
		shell = new Shell();
		shell.setSize(207, 130);
		shell.setText("Zmiana ceny");

		textNowa = new Text(shell, SWT.BORDER);
		textNowa.setBounds(10, 10, 169, 26);
		textNowa.setText(String.valueOf(((Produkt) obiekt).cena));

		Button btnZatwierdz = new Button(shell, SWT.NONE);
		btnZatwierdz.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				String string = textNowa.getText();
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				Boolean check = true;
				int kropka=0;
				for (int i = 0; i < chars.length; i++) {
					if (!(chars[i] == '.' && kropka==0) && !('0' <= chars[i] && chars[i] <= '9')) {
						check = false;
						break;
					}
					if (chars[i] == '.') kropka++;
				}
				if (check) {
					((Produkt) obiekt).setCena(Double.parseDouble(textNowa.getText()));
				}
				shell.close();
			}
		});
		btnZatwierdz.setBounds(10, 42, 78, 30);
		btnZatwierdz.setText("Zatwierd\u017A");

		Button btnAnuluj = new Button(shell, SWT.NONE);
		btnAnuluj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnAnuluj.setBounds(101, 42, 78, 30);
		btnAnuluj.setText("Anuluj");

	}
}
