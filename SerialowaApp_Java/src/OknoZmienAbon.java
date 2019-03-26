import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/** Okno do zmiany ceny abonamentu
 * @author Szymon
 *
 */
public class OknoZmienAbon {

	protected Shell shell;
	private Text textBasic;
	private Text textFamily;
	private Text textPremium;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	/*public static void main(String[] args) 
	{
		try {
			OknoZmienAbon window = new OknoZmienAbon();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * Open the window.
	 */
	public void open(SystemAplikacji system) {
		Display display = Display.getDefault();
		createContents(system);
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
	protected void createContents(SystemAplikacji system) {
		shell = new Shell();
		shell.setSize(223, 196);
		shell.setText("Zmiana abonamentu");

		Label lblBasic = new Label(shell, SWT.NONE);
		lblBasic.setAlignment(SWT.RIGHT);
		lblBasic.setBounds(29, 13, 70, 20);
		lblBasic.setText("Basic:");

		textBasic = new Text(shell, SWT.BORDER);
		textBasic.setBounds(105, 10, 90, 26);
		textBasic.setText(String.valueOf(Abonament.getBasic()));

		textFamily = new Text(shell, SWT.BORDER);
		textFamily.setBounds(105, 42, 90, 26);
		textFamily.setText(String.valueOf(Abonament.getFamily()));

		textPremium = new Text(shell, SWT.BORDER);
		textPremium.setBounds(105, 74, 90, 26);
		textPremium.setText(String.valueOf(Abonament.getPremium()));

		Label lblFamily = new Label(shell, SWT.NONE);
		lblFamily.setAlignment(SWT.RIGHT);
		lblFamily.setBounds(29, 45, 70, 20);
		lblFamily.setText("Family:");

		Label lblPremium = new Label(shell, SWT.NONE);
		lblPremium.setAlignment(SWT.RIGHT);
		lblPremium.setBounds(29, 77, 70, 20);
		lblPremium.setText("Premium:");

		Button btnZatwierdz = new Button(shell, SWT.NONE);
		btnZatwierdz.setBounds(9, 112, 90, 30);
		btnZatwierdz.setText("Zatwierd\u017A");
		btnZatwierdz.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Double przedB = Abonament.getBasic();
				Double przedF = Abonament.getFamily();
				Double przedP = Abonament.getPremium();

				String string = textBasic.getText();
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				Boolean check = true;
				int kropka = 0;
				for (int i = 0; i < chars.length; i++) {
					if (!(chars[i] == '.' && kropka==0) && !('0' <= chars[i] && chars[i] <= '9')) {
						check = false;
						break;
					}
					if (chars[i] == '.') kropka++;
				}
				if (check)
					Abonament.setBasic(Double.parseDouble(textBasic.getText()));
				System.out.println(Abonament.getBasic());
				System.out.println(textBasic.getText());
				string = textFamily.getText();
				char[] chars1 = new char[string.length()];
				string.getChars(0, chars1.length, chars1, 0);
				check = true;
				kropka = 0;
				for (int i = 0; i < chars1.length; i++) {
					if (!(chars1[i] == '.' && kropka==0) && !('0' <= chars1[i] && chars1[i] <= '9')) {
						check = false;
						break;
					}
					if (chars1[i] == '.') kropka++;
				}
				if (check)
					Abonament.setFamily(Double.parseDouble(textFamily.getText()));

				string = textPremium.getText();
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				check = true;
				kropka = 0;
				for (int i = 0; i < chars2.length; i++) {
					if (!(chars2[i] == '.' && kropka==0) && !('0' <= chars2[i] && chars2[i] <= '9')) {
						check = false;
						break;
					}
					if (chars2[i] == '.') kropka++;
				}
				if (check)
					Abonament.setPremium(Double.parseDouble(textPremium.getText()));

				synchronized (Uzytkownik.class) {
					for (Klient k : system.getKlienci()) {
						if (k.getAbonament().getJaki().equals("Basic") && !przedB.equals(Abonament.getBasic()))
							k.getAbonament().setCena(Abonament.getBasic());
						else if (k.getAbonament().getJaki().equals("Family") && !przedF.equals(Abonament.getFamily()))
							k.getAbonament().setCena(Abonament.getFamily());
						else if (k.getAbonament().getJaki().equals("Premium") && !przedP.equals(Abonament.getPremium()))
							k.getAbonament().setCena(Abonament.getPremium());
					}
					textBasic.setText(String.valueOf(Abonament.getBasic()));
					textFamily.setText(String.valueOf(Abonament.getFamily()));
					textPremium.setText(String.valueOf(Abonament.getPremium()));
				}
				shell.close();
			}
		});

		Button btnAnuluj = new Button(shell, SWT.NONE);
		btnAnuluj.setBounds(105, 112, 90, 30);
		btnAnuluj.setText("Anuluj");
		btnAnuluj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

	}
}
