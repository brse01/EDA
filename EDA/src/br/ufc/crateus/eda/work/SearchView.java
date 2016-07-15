package br.ufc.crateus.eda.work;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.hashing.LinearProbingHashST;
import br.ufc.crateus.eda.st.hashing.SeparateChainingHashST;
import br.ufc.crateus.eda.st.hashing.SeparateTreeHash;
import br.ufc.crateus.eda.st.ordered.BinarySearchTree;
import br.ufc.crateus.eda.string.StringST;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class SearchView extends Application {
	private int type;
	private String path;
	private String name;
	private Pane pane;
	private Scene scene;
	private Button performButton;
	private TextField searchTextField;
	private Button porButton;
	private Button inglButton;
	private Button leftButton;
	private Button rightButton;
	private Button searchButton;
	private TextArea listTextArea;
	private TextArea listSurgeTextArea;
	private Image image;
	private ImageView pictureImageView;
	private ImageView pictureLogoImageView;
	private long firstProcess;
	private long endProcess;
	List<String> nameProcessos = new ArrayList<>();
	List<Double> timeProcessos = new ArrayList<>();
	Double auxTime = 0.0;

	Versao1 ver1;
	SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> mapSeparateChainingHashST;
	SeparateChainingHashST<String, Integer> codIdSeparateChainingHashST;

	Versao2 ver2;
	LinearProbingHashST<String, BinarySearchTree<Integer, Integer>> mapLinearProbingHashST;
	LinearProbingHashST<String, Integer> codIdLinearProbingHashST;

	Versao3 ver3;
	SeparateTreeHash<String, BinarySearchTree<Integer, Integer>> mapSeparateTreeHash;
	SeparateTreeHash<String, Integer> codIdSeparateTreeHash;
	List<String> listFile = new ArrayList<>();
	List<Integer> numberWords = new ArrayList<>();
	StringST<String> suggestions = new StringST<>();
	BinarySearchTree<Double, String> toPrint = new BinarySearchTree<>();
	List<String> words;
	private List<String> aux = new ArrayList<>();

	private void initComponents() throws IOException {

		performButton = new Button("Search");
		searchTextField = new TextField();
		listTextArea = new TextArea();
		listSurgeTextArea = new TextArea();
		listSurgeTextArea.setPrefSize(500, 210);

		image = new Image(getClass().getResourceAsStream("/br/ufc/crateus/eda/work/photo/lupa.png"));
		pictureImageView = new ImageView(new Image("/br/ufc/crateus/eda/work/photo/caçador.png"));
		pictureLogoImageView = new ImageView(new Image("/br/ufc/crateus/eda/work/photo/logo.png"));

		searchButton = new Button();
		searchButton.setGraphic(new ImageView(image));
		searchButton.setPrefSize(50, 40);
		leftButton = new Button("Pesquisa Caçador");
		leftButton.setBorder(null);
		rightButton = new Button("Estou com sorte");
		porButton = new Button("Português (Brasil)");
		inglButton = new Button("Inglês (US)");
		porButton.setStyle("-fx-background-color: #FFFFFF");
		inglButton.setStyle("-fx-background-color: #FFFFFF");
		searchButton.setStyle("-fx-background-color:#1E90FF");

		listSurgeTextArea.setVisible(false);
		listTextArea.setBorder(null);
		listTextArea.setFont(new Font(16));
		listSurgeTextArea.setFont(new Font(14));
		listTextArea.setPrefSize(500, 210);
		listTextArea.setVisible(false);
		listTextArea.setEditable(false);
		listSurgeTextArea.setEditable(false);

		searchTextField.setPrefSize(500, 40);
		pane = new AnchorPane();
		scene = new Scene(pane, 900, 500);
		suggestions = new StringST<>();
		numberWords = new ArrayList<>();
		nameProcessos.add(0, "Criar Indice Invertido");
		nameProcessos.add(1, "Sugestões");
		nameProcessos.add(2, "Realizar Busca");
	}

	public void start(Stage primaryStage, int type, String path, String name) {
		this.type = type;
		this.path = path;
		this.name = name;
		try {
			start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initComponents();
		initListeners();
		firstProcess = time();
		choose(type, path, name);
		endProcess = time();
		System.err.println(endProcess - firstProcess / 1000d);
		auxTime = calculoTime(endProcess, firstProcess);
		timeProcessos.add(0, auxTime);
		pane.getChildren().addAll(searchTextField, porButton, inglButton, listTextArea, leftButton, rightButton,
				listSurgeTextArea, searchButton, pictureImageView, pictureLogoImageView);
		pane.setStyle("-fx-background-color: #FFFFFF");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Busca");
		primaryStage.getIcons().add(new Image("/br/ufc/crateus/eda/work/photo/caçador.png"));
		primaryStage.setResizable(false);
		primaryStage.setMaximized(false);
		primaryStage.show();
		initLayout();
	}

	private void initLayout() {
		searchTextField.setLayoutX(400 / 2);
		searchTextField.setLayoutY(250 / 2);

		pictureLogoImageView.setLayoutX(450 / 2);
		pictureLogoImageView.setLayoutY(15);

		pictureImageView.setLayoutX(685);
		pictureImageView.setLayoutY(80);

		searchButton.setLayoutX(650);
		searchButton.setLayoutY(250 / 2);

		listTextArea.setLayoutX(400 / 2);
		listTextArea.setLayoutY(240);

		listSurgeTextArea.setLayoutX(400 / 2);
		listSurgeTextArea.setLayoutY(163);

		leftButton.setLayoutX(320);
		leftButton.setLayoutY(190);
		rightButton.setLayoutX(480);
		rightButton.setLayoutY(190);

		porButton.setLayoutX(480);
		porButton.setLayoutY(460);

		inglButton.setLayoutX(320);
		inglButton.setLayoutY(460);

	}

	private void clean() {
	}

	private void initListeners() {

		porButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Port();
			}
		});

		inglButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				US();
			}
		});

		searchTextField.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
			int x = 0;

			@Override
			public void handle(KeyEvent event) {
				if (!searchTextField.getText().equals("")) {
					x++;
					listTextArea.setVisible(false);
					listSurgeTextArea.setVisible(false);
					firstProcess = time();
					List<String> aux = (List<String>) typeSuggestions(searchTextField.getText(), type);
					endProcess = time();
					if (aux.size() > 0) {
						leftButton.setVisible(false);
						rightButton.setVisible(false);
						FillSurgeTextArea(aux, type);
						listSurgeTextArea.setVisible(true);
					}
				} else {
					leftButton.setVisible(true);
					rightButton.setVisible(true);
					listSurgeTextArea.setVisible(false);
				}

				auxTime = calculoTime(endProcess, firstProcess);
				firstProcess = 0;
				endProcess = 0;
				if (x == 1) {
					timeProcessos.add(1, auxTime);
					x = 0;
				}
			}

		});

		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!searchTextField.getText().equals("")) {
					firstProcess = time();
					realizeSearch(type);
					endProcess = time();
					timeProcessos.add(2, calculoTime(endProcess, firstProcess));
					saveTime(timeProcessos, nameProcessos, type, path);
					leftButton.setVisible(false);
					rightButton.setVisible(false);
					listSurgeTextArea.setVisible(false);
					listTextArea.setVisible(true);
					searchTextField.setText("");
				}
			}

		});

		rightButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!searchTextField.getText().equals("")) {
					firstProcess = time();
					realizeSearch(type);
					endProcess = time();
					timeProcessos.add(2, calculoTime(endProcess, firstProcess));
					saveTime(timeProcessos, nameProcessos, type, path);
					leftButton.setVisible(false);
					rightButton.setVisible(false);
					listSurgeTextArea.setVisible(false);
					listTextArea.setVisible(true);
					searchTextField.setText("");
				} else {
					Erro("Por gentileza Digite algum termo. Para que sua perguisa tenha um resultado eficiente",
							AlertType.INFORMATION, "Atenção");
				}
			}
		});

		leftButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!searchTextField.getText().equals("")) {
					firstProcess = time();
					realizeSearch(type);
					endProcess = time();
					timeProcessos.add(2, calculoTime(endProcess, firstProcess));
					saveTime(timeProcessos, nameProcessos, type, path);
					leftButton.setVisible(false);
					rightButton.setVisible(false);
					listSurgeTextArea.setVisible(false);
					listTextArea.setVisible(true);
					searchTextField.setText("");
				} else {
					Erro("Por gentileza Digite algum termo. Para que sua perguisa tenha um resultado eficiente",
							AlertType.INFORMATION, "Atenção");
				}
			}

		});

		searchTextField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!searchTextField.getText().equals("")) {
					firstProcess = time();
					realizeSearch(type);
					endProcess = time();
					timeProcessos.add(2, calculoTime(endProcess, firstProcess));
					saveTime(timeProcessos, nameProcessos, type, path);
					leftButton.setVisible(false);
					rightButton.setVisible(false);
					listSurgeTextArea.setVisible(false);
					listTextArea.setVisible(true);
					searchTextField.setText("");
				} else {
					Erro("Por gentileza Digite algum termo. Para que sua perguisa tenha um resultado eficiente",
							AlertType.INFORMATION, "Atenção");
				}
			}
		});
	}

	public void Port() {
		leftButton.setText("Pesquisa Caçador");
		rightButton.setText("Estou com sorte");
	}

	public void US() {
		leftButton.setText("Search Hunter");
		rightButton.setText("I'm Feeling Lucky");
	}

	private void Erro(String er, AlertType type, String Header) {
		Alert alert = new Alert(type);
		alert.setHeaderText(Header);
		alert.setContentText(er);
		alert.showAndWait();
	}

	private void FillTextArea(BinarySearchTree<Double, String> toPrint, int type) {
		listTextArea.setText("");
		List<Double> decreasing = new ArrayList<>();
		if (type == 1) {
			ver1.decreasing(toPrint, decreasing);
		} else if (type == 2) {
			ver2.decreasing(toPrint, decreasing);
		} else {
			ver3.decreasing(toPrint, decreasing);
		}

		int size = decreasing.size();
		if (size > 0) {
			String name = toPrint.get(decreasing.get(0));
			for (int i = 0; i < size; i++) {
				listTextArea.setText(listTextArea.getText() + toPrint.get(decreasing.get(i)) + "\n");
			}

			try {
				Runtime.getRuntime().exec("notepad " + this.path + name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Erro("Ops! Desculpe a fraqueza do nosso banco de dados, nada foi encontrado. Tente novamente :)",
					AlertType.INFORMATION, "Ops !");
			listTextArea.setText("Nada Encontrado :(");
		}
	}

	private void FillSurgeTextArea(List<String> suggestions, int type) {

		listSurgeTextArea.setText("");
		int size = suggestions.size();
		if (size < 8) {
			for (int i = 0; i < size; i++) {
				listSurgeTextArea.setText(listSurgeTextArea.getText() + suggestions.get(i) + "\n");
			}
		} else {
			for (int i = 0; i < 8; i++) {
				listSurgeTextArea.setText(listSurgeTextArea.getText() + suggestions.get(i) + "\n");
			}
		}

	}

	private void realizeSearch(int type) {
		String line = searchTextField.getText();
		if (type == 1) {
			words = ver1.separateWord(line);
			toPrint = ver1.calculate(words, codIdSeparateChainingHashST, numberWords, mapSeparateChainingHashST,
					listFile);
		} else if (type == 2) {
			words = ver2.separateWord(line);
			System.err.println(words);
			toPrint = ver2.calculate(words, codIdLinearProbingHashST, numberWords, mapLinearProbingHashST, listFile);
		} else {
			words = ver3.separateWord(line);
			toPrint = ver3.calculate(words, codIdSeparateTreeHash, numberWords, mapSeparateTreeHash, listFile);
		}
		searchTextField.setText("");
		FillTextArea(toPrint, type);
	}

	private void type1(String path, String name) throws IOException {
		ver1 = new Versao1();
		listFile = ver1.captureEnter(path + name);
		System.out.println(listFile.size());
		codIdSeparateChainingHashST = ver1.docId(listFile);
		System.out.println(codIdSeparateChainingHashST.size());
		mapSeparateChainingHashST = ver1.createInvertedIndex2(codIdSeparateChainingHashST, numberWords, suggestions,
				path);
	}

	private void type2(String path, String name) throws IOException {
		ver2 = new Versao2();
		listFile = ver2.captureEnter(path + name);
		codIdLinearProbingHashST = ver2.docId(listFile);
		mapLinearProbingHashST = ver2.createInvertedIndex2(codIdLinearProbingHashST, numberWords, suggestions, path);
	}

	private void type3(String path, String name) throws IOException {
		ver3 = new Versao3();
		listFile = ver3.captureEnter(path + name);
		codIdSeparateTreeHash = ver3.docId(listFile);
		mapSeparateTreeHash = ver3.createInvertedIndex2(codIdSeparateTreeHash, numberWords, suggestions, path);
	}

	private Iterable<String> typeSuggestions(String str, int type) {
		if (type == 1) {
			return suggestions.keysWithPrefix(ver1.normalizeStr(str));
		}
		if (type == 2) {
			return suggestions.keysWithPrefix(ver2.normalizeStr(str));
		}
		return suggestions.keysWithPrefix(ver3.normalizeStr(str));
	}

	private void choose(int type, String path, String name) throws IOException {
		if (type == 1) {
			type1(path, name);
		} else if (type == 2) {
			type2(path, name);
		} else {
			type3(path, name);
		}
	}

	private long time() {
		return System.currentTimeMillis();
	}

	private double calculoTime(long endProcess, long firstProcess) {
		return (endProcess - firstProcess / 1000d);
	}

	private String type(int type) {
		if (type == 1)
			return "SeparateChainingHashST";
		else if (type == 2)
			return "LinearProbingHashST";
		else
			return "SeparateTreeHash";
	}

	private void saveTime(List<Double> timeProcessos, List<String> nameProcessos, int type, String path) {
		File file = new File(path + type(type) + ".txt");
		try {
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter pw = new PrintWriter(file);

			pw.println("|TIPO DE IMPLEMENTAÇÃO: " + type(type) + " |");
			pw.println("-------------------------------------------");
			pw.println("|        Função       |  Tempo	|");
			pw.println("-------------------------------------------");
			for (int i = 0; i < nameProcessos.size(); i++) {
				pw.println(nameProcessos.get(i));
				pw.println(timeProcessos.get(i));
			}
			timeProcessos = null;
			pw.close();
			fileWriter.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
