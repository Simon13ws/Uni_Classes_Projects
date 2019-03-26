import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import java.util.LinkedHashSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * G³ówne okno programu - wyœwietlaj¹ce wszystkie zbiory z systemu
 * 
 * @author Szymon
 *
 */

public class OknoGlowne {
	/**
	 * Pole tekstowe do wyszukiwania produktów w systemie - po nazwie produktu lub
	 * po nazwie aktora wystêpuj¹cego w produkcie (nie konweruje ma³ych liter na
	 * duze ani na odwrót, trzeba wpisaæ dok³adnie nazwê szukanego produktu/aktora)
	 */
	private Text textWyszukiwanie;

	/**
	 * Panel, z którego program siê rozpocz¹³ - od niego ma dostêp do systemu
	 */
	private PanelKontrolny panel;
	/**
	 * Lista z produktami
	 */
	private List listP;
	/**
	 * Lista z klientami
	 */
	private List listK;
	/**
	 * Lista z dystrybutorami
	 */
	private List listD;
	/**
	 * Label z przychodem systemu z ostatniego miesi¹ca
	 */
	private Label lblPrzy;
	/**
	 * Przycisk, z którego przechodzimy do okna, w którym mo¿na zmieniæ ceny
	 * abonamentów
	 */
	private Button btnZmieCenAbonamentw;
	/**
	 * Przycisk, przez który u¿ytkownik mo¿e dodaæ dystrybutora do systemu
	 */
	private Button btnDodajDystrybutora;
	/**
	 * Zmienna sprawdzaj¹ca, czy symulacja nie ma siê zakoñczyæ, z racji strat przez
	 * ostatnie 3 miesi¹ce
	 */
	int koniec = 0;
	private String przychod = "0";
	/**
	 * Lista wyœwietlanych produktów
	 */
	private LinkedHashSet<Produkt> produkty;
	/**
	 * Lista wyœwietlanych klientów
	 */
	private LinkedHashSet<Klient> klienci;
	/**
	 * Lista wyœwietlanych dystrybutorów
	 */
	private LinkedHashSet<Dystrybutor> dystrybutorzy;

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
	 * 
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		produkty = new LinkedHashSet<Produkt>();
		klienci = new LinkedHashSet<Klient>();
		dystrybutorzy = new LinkedHashSet<Dystrybutor>();
		while (!shell.isDisposed()) {
			if (textWyszukiwanie.getText().isEmpty()) {

				if (koniec == 1) {
					Label lblKoniecSymulacjiOstatnie = new Label(shell, SWT.NONE);
					lblKoniecSymulacjiOstatnie.setAlignment(SWT.CENTER);
					lblKoniecSymulacjiOstatnie.setBounds(44, 100, 262, 89);
					lblKoniecSymulacjiOstatnie.setText(
							"Koniec symulacji! Ostatnie trzy miesi\u0105cy przynios\u0142y tylko straty, dlatego system zosta\u0142 zamkni\u0119ty.");

					btnZmieCenAbonamentw.dispose();
					btnDodajDystrybutora.dispose();
				}
				synchronized (Uzytkownik.class) {
					for (Produkt p : panel.getSystem().getProdukty())
						if (!produkty.contains(p))
							listP.add(p.getNazwa());

					for (Produkt p : produkty)
						if (!panel.getSystem().getProdukty().contains(p))
							listP.remove(p.getNazwa());
					produkty = new LinkedHashSet<Produkt>(panel.getSystem().getProdukty());

					for (Klient k : panel.getSystem().getKlienci())
						if (!klienci.contains(k))
							listK.add(k.getNazwa());
					for (Klient k : klienci)
						if (!panel.getSystem().getKlienci().contains(k))
							listK.remove(k.getNazwa());

					klienci = new LinkedHashSet<Klient>(panel.getSystem().getKlienci());

					for (Dystrybutor d : panel.getSystem().getDystrybutorzy())
						if (!dystrybutorzy.contains(d))
							listD.add(d.getNazwa());
					for (Dystrybutor d : dystrybutorzy)
						if (!panel.getSystem().getDystrybutorzy().contains(d))
							listD.remove(d.getNazwa());
					dystrybutorzy = new LinkedHashSet<Dystrybutor>(panel.getSystem().getDystrybutorzy());

					// Zrobic zeby co miesiac (?) sie aktualizowalo i sprawdzic czemu tak szybko
					// wzrasta
					lblPrzy.setText(przychod);
				}
			}
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell();
		shell.setSize(1191, 506);
		shell.setText("System zarz¹dzania");

		listP = new List(shell, SWT.BORDER);
		listP.setBounds(340, 39, 252, 397);

		listP.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				Obiekt o = new Obiekt();
				if (!(listP.getSelectionCount() == 0))
					for (Produkt p : panel.getSystem().getProdukty())
						if (p.getNazwa().equals(listP.getSelection()[0])) {
							o = p;
							break;
						}
				try {
					OknoInfo window = new OknoInfo();
					window.open(o, panel);
				} catch (Exception excep) {
					excep.printStackTrace();
				}
			}
		});

		listD = new List(shell, SWT.BORDER);
		listD.setBounds(609, 39, 252, 397);
		listD.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				Obiekt o = new Obiekt();
				if (!(listD.getSelectionCount() == 0))
					for (Dystrybutor d : panel.getSystem().getDystrybutorzy())
						if (d.getNazwa().equals(listD.getSelection()[0])) {
							o = d;
							break;
						}
				try {
					OknoInfo window = new OknoInfo();
					window.open(o, panel);
				} catch (Exception excep) {
					excep.printStackTrace();
				}
			}
		});

		listK = new List(shell, SWT.BORDER);
		listK.setBounds(878, 39, 252, 397);
		listK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				Obiekt o = new Obiekt();
				if (!(listK.getSelectionCount() == 0))
					for (Klient k : panel.getSystem().getKlienci())
						if (k.getNazwa().equals(listK.getSelection()[0])) {
							o = k;
							break;
						}
				try {
					OknoInfo window = new OknoInfo();
					window.open(o, panel);
				} catch (Exception excep) {
					excep.printStackTrace();
				}
			}
		});

		textWyszukiwanie = new Text(shell, SWT.BORDER);
		textWyszukiwanie.setBounds(44, 40, 262, 25);

		// Dodaje produkty na liste ze wzgledu na pole wyszukiwania tekstowego - od
		// nazwy produktow i aktorow w nagraniach
		textWyszukiwanie.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				listP.removeAll();
				LinkedHashSet<String> zbior = new LinkedHashSet<>();
				String napis = ((Text) e.widget).getText();
				for (Produkt p : panel.getSystem().getProdukty()) {
					if (napis.isEmpty() || (p.getNazwa().length() >= napis.length()
							&& napis.equals(p.getNazwa().substring(0, napis.length()))))
						zbior.add(p.getNazwa());
					if (p.toString().equals("Serial") || p.toString().equals("Film")) {
						for (String a : ((Nagranie) p).listaAktorow) {
							if (a.length() >= napis.length() && napis.equals(a.substring(0, napis.length()))) {
								zbior.add(p.getNazwa());
							}
						}
					}
				}
				for (String s : zbior)
					listP.add(s);
			}
		});

		Label lblListaProduktw = new Label(shell, SWT.NONE);
		lblListaProduktw.setBounds(340, 10, 120, 20);
		lblListaProduktw.setText("Lista produkt\u00F3w:");

		btnDodajDystrybutora = new Button(shell, SWT.NONE);
		btnDodajDystrybutora.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				panel.dodajDystrybutora();
			}
		});
		btnDodajDystrybutora.setBounds(44, 100, 262, 30);
		btnDodajDystrybutora.setText("Dodaj dystrybutora");

		Label lblListaDystrybutorw = new Label(shell, SWT.NONE);
		lblListaDystrybutorw.setBounds(609, 10, 140, 20);
		lblListaDystrybutorw.setText("Lista dystrybutor\u00F3w:");

		Label lblListaKlientw = new Label(shell, SWT.NONE);
		lblListaKlientw.setBounds(878, 10, 98, 20);
		lblListaKlientw.setText("Lista klient\u00F3w:");

		Label lblSzukajProduktu = new Label(shell, SWT.NONE);
		lblSzukajProduktu.setBounds(44, 14, 120, 20);
		lblSzukajProduktu.setText("Szukaj produktu:");

		Label lblPrzychod = new Label(shell, SWT.NONE);
		lblPrzychod.setAlignment(SWT.CENTER);
		lblPrzychod.setBounds(44, 228, 262, 20);
		lblPrzychod.setText("Przych\u00F3d z ostatniego miesi\u0105ca:");

		lblPrzy = new Label(shell, SWT.NONE);
		lblPrzy.setAlignment(SWT.CENTER);
		lblPrzy.setBounds(27, 262, 301, 20);

		btnZmieCenAbonamentw = new Button(shell, SWT.NONE);
		btnZmieCenAbonamentw.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					OknoZmienAbon window = new OknoZmienAbon();
					window.open(panel.getSystem());
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		});
		btnZmieCenAbonamentw.setBounds(44, 160, 262, 30);
		btnZmieCenAbonamentw.setText("Zmie\u0144 cen\u0119 abonament\u00F3w");

		Button btnWyjdz = new Button(shell, SWT.NONE);
		btnWyjdz.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				synchronized (Uzytkownik.class) {
					for (Dystrybutor d : panel.getSystem().getDystrybutorzy())
						d.interrupt();
				}

				System.exit(0);
			}
		});
		btnWyjdz.setBounds(44, 387, 262, 30);
		btnWyjdz.setText("Wyjd\u017A");

		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				synchronized (Uzytkownik.class) {
					for (Dystrybutor d : panel.getSystem().getDystrybutorzy())
						d.interrupt();
				}
				System.exit(0);
			}
		});

	}

	public void koniec() {

	}

	public PanelKontrolny getPanel() {
		return panel;
	}

	public void setPanel(PanelKontrolny panel) {
		this.panel = panel;
	}

	public Label getLblPrzy() {
		return lblPrzy;
	}

	/**
	 * Ustawia przychód w string z dok³adnoœcia do 2 miejsc po przecinku
	 */
	public void setLblPrzy() {
		przychod = String.format("%.2f", panel.getSystem().getPrzychod());
	}

	public int getKoniec() {
		return koniec;
	}

	public void setKoniec(int koniec) {
		this.koniec = koniec;
	}

	protected Shell shell;

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public Text getTextWyszukiwanie() {
		return textWyszukiwanie;
	}

	public void setTextWyszukiwanie(Text textWyszukiwanie) {
		this.textWyszukiwanie = textWyszukiwanie;
	}

	public List getListP() {
		return listP;
	}

	public void setListP(List listP) {
		this.listP = listP;
	}

	public List getListK() {
		return listK;
	}

	public void setListK(List listK) {
		this.listK = listK;
	}

	public List getListD() {
		return listD;
	}

	public void setListD(List listD) {
		this.listD = listD;
	}

	public String getPrzychod() {
		return przychod;
	}

	public void setPrzychod(String przychod) {
		this.przychod = przychod;
	}

	public LinkedHashSet<Produkt> getProdukty() {
		return produkty;
	}

	public void setProdukty(LinkedHashSet<Produkt> produkty) {
		this.produkty = produkty;
	}

	public LinkedHashSet<Klient> getKlienci() {
		return klienci;
	}

	public void setKlienci(LinkedHashSet<Klient> klienci) {
		this.klienci = klienci;
	}

	public LinkedHashSet<Dystrybutor> getDystrybutorzy() {
		return dystrybutorzy;
	}

	public void setDystrybutorzy(LinkedHashSet<Dystrybutor> dystrybutorzy) {
		this.dystrybutorzy = dystrybutorzy;
	}

	public void setLblPrzy(Label lblPrzy) {
		this.lblPrzy = lblPrzy;
	}

}