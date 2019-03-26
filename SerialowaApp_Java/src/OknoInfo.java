import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import java.text.SimpleDateFormat;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

/**
 * Okno, w którym wyœwietlane s¹ informacje o obiekcie (produkcie, kliencie,
 * dystrybutorze), ma przyciski do przejœcia do okien z opisem, z wykresem, ze
 * zmian¹ ceny produktu i przycisk umo¿liwiaj¹cy usuniêcie obiektu z systemu
 * 
 * @author Szymon
 *
 */
public class OknoInfo {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { try { OknoInfo window = new
	 * OknoInfo(); window.open(); } catch (Exception e) { e.printStackTrace(); } }
	 */
	/**
	 * Open the window.
	 */
	public <T extends Obiekt> void open(T obiekt, PanelKontrolny panel) {
		Display display = Display.getDefault();
		createContents(obiekt, panel, display);

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
	protected <T extends Obiekt> void createContents(T obiekt, PanelKontrolny panel, Display display) {

		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");

		shell = new Shell();
		shell.setSize(450, 341);
		shell.setText("Informacje o obiekcie");

		Label lblKtoCo = new Label(shell, SWT.NONE);
		lblKtoCo.setBounds(10, 17, 162, 20);
		lblKtoCo.setText(obiekt.toString());

		Label lblNazwa = new Label(shell, SWT.NONE);
		lblNazwa.setBounds(10, 43, 261, 20);
		lblNazwa.setText(obiekt.getNazwa());

		Button btnUsun = new Button(shell, SWT.NONE);
		btnUsun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				panel.getSystem().UsunElement(obiekt);
				shell.close();
			}
		});
		btnUsun.setBounds(305, 10, 117, 30);
		btnUsun.setText("Usu\u0144");

		if (obiekt.toString().equals("Klient") || obiekt.toString().equals("Dystrybutor")) {
			Label lblMail = new Label(shell, SWT.NONE);
			lblMail.setBounds(10, 62, 200, 20);
			lblMail.setText(((Uzytkownik) obiekt).getEmail());

			if (obiekt.toString().equals("Klient")) {
				Klient k = (Klient) obiekt;
				Label lblNrKarty = new Label(shell, SWT.NONE);
				lblNrKarty.setBounds(10, 126, 60, 20);
				lblNrKarty.setText("Nr karty:");

				Label lblDataUrodzenia = new Label(shell, SWT.NONE);
				lblDataUrodzenia.setBounds(10, 100, 110, 20);
				lblDataUrodzenia.setText("Data urodzenia:");

				Label lblData = new Label(shell, SWT.NONE);
				lblData.setBounds(127, 100, 234, 20);
				lblData.setText(dataFormat.format(k.getDataUrodzenia().getTime())); // USTAWIC BY BYLA ODPOWIEDNIA DATA

				Label lblNKarty = new Label(shell, SWT.NONE);
				lblNKarty.setBounds(75, 126, 286, 20);
				lblNKarty.setText(k.getNrKarty().toString());

				Label lblAbonament = new Label(shell, SWT.NONE);
				lblAbonament.setBounds(10, 152, 89, 20);
				lblAbonament.setText("Abonament:");

				Label lblAb = new Label(shell, SWT.NONE);
				lblAb.setBounds(105, 152, 70, 20);
				lblAb.setText(((Klient) obiekt).getAbonament().getJaki());
			} else if (obiekt.toString().equals("Dystrybutor")) {
				Dystrybutor d = (Dystrybutor) obiekt;

				Label lblNrKonta = new Label(shell, SWT.NONE);
				lblNrKonta.setBounds(10, 93, 70, 20);
				lblNrKonta.setText("Nr konta: ");

				Label lblNkonta = new Label(shell, SWT.NONE);
				lblNkonta.setText(d.getNrKonta().toString());
				lblNkonta.setBounds(96, 93, 286, 20);

				Label lblProduktyTegoDystrybutora = new Label(shell, SWT.NONE);
				lblProduktyTegoDystrybutora.setBounds(10, 119, 190, 20);
				lblProduktyTegoDystrybutora.setText("Produkty tego dystrybutora:");

				List list = new List(shell, SWT.BORDER);
				list.setBounds(10, 145, 412, 98);
				for (Produkt p : d.getWlasneProdukty())
					list.add(p.nazwa);
			}
		} else {

			Image obrazek = new Image(display, ((Produkt) obiekt).getZdjecie());

			Button btnObraz = new Button(shell, SWT.NONE);
			btnObraz.setBounds(305, 126, 117, 158);
			btnObraz.setImage(obrazek);

			Button btnOpis = new Button(shell, SWT.NONE);
			btnOpis.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						OknoOpis window = new OknoOpis();
						window.open((Produkt) obiekt);
					} catch (Exception err) {
						err.printStackTrace();
					}
				}
			});
			btnOpis.setBounds(209, 10, 90, 30);
			btnOpis.setText("Opis");

			Label lblCena = new Label(shell, SWT.NONE);
			lblCena.setBounds(10, 91, 54, 20);
			lblCena.setText("Cena:");

			Label lblCen = new Label(shell, SWT.NONE);
			lblCen.setBounds(90, 91, 82, 20);
			lblCen.setText(String.valueOf(((Produkt) obiekt).getCena()) + "z³");

			Label lblWyswietlen = new Label(shell, SWT.NONE);
			lblWyswietlen.setBounds(10, 143, 86, 20);
			lblWyswietlen.setText("Wy\u015Bwietle\u0144:");

			Label lblWysw = new Label(shell, SWT.NONE);
			lblWysw.setBounds(102, 143, 70, 20);
			lblWysw.setText(String.valueOf(((Produkt) obiekt).getWyswietlenia()));

			Label lblKrajProdukcji = new Label(shell, SWT.NONE);
			lblKrajProdukcji.setBounds(10, 117, 101, 20);
			lblKrajProdukcji.setText("Kraj produkcji:");

			Label lblkraj = new Label(shell, SWT.NONE);
			lblkraj.setBounds(124, 117, 136, 20);
			lblkraj.setText(((Produkt) obiekt).getKrajProdukcji());

			if (((Produkt) obiekt).getPierwszyTydzien() != null) {
				Button btnWykres = new Button(shell, SWT.NONE);
				btnWykres.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {

						Produkt p = (Produkt) obiekt;

						// adjust the axis range
						try {
							OknoWykres window = new OknoWykres();
							window.open(p);
						} catch (Exception err) {
							err.printStackTrace();
						}

						// Wykres wykres = new Wykres(p);
						// wykres.start();

					}
				});
				btnWykres.setBounds(305, 82, 117, 30);
				btnWykres.setText("Wykres");
			}
			Button btnZmien = new Button(shell, SWT.NONE);
			btnZmien.setBounds(305, 46, 117, 30);
			btnZmien.setText("Zmie\u0144 cen\u0119");

			btnZmien.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						OknoZmienCene window = new OknoZmienCene();
						window.open(obiekt);
					} catch (Exception err) {
						err.printStackTrace();
					}
					lblCen.setText(String.valueOf(((Produkt) obiekt).getCena()));
				}
			});

			if (obiekt.toString().equals("Livestream")) {

				Label lblData_2 = new Label(shell, SWT.NONE);
				lblData_2.setBounds(10, 65, 70, 20);
				lblData_2.setText("Data:");

				Label labelDataW = new Label(shell, SWT.NONE);
				labelDataW.setBounds(90, 65, 220, 20);
				labelDataW.setText(((Livestream) obiekt).getDataWydarzenia().getTime().toString());

			} else {

				Label lblData_1 = new Label(shell, SWT.NONE);
				lblData_1.setBounds(10, 65, 109, 20);
				lblData_1.setText("Data produkcji:");

				Label lblDataPr = new Label(shell, SWT.NONE);
				lblDataPr.setBounds(135, 65, 136, 20);
				lblDataPr.setText(dataFormat.format(((Produkt) obiekt).getData().getTime()));

				Label lblAktorzy = new Label(shell, SWT.NONE);
				lblAktorzy.setBounds(10, 169, 70, 20);
				lblAktorzy.setText("Aktorzy:");

				List list_aktorzy = new List(shell, SWT.BORDER);
				list_aktorzy.setBounds(10, 195, 215, 89);

				for (String a : ((Nagranie) obiekt).getListaAktorow())
					list_aktorzy.add(a);

			}
		}

	}
}
