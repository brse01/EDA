package br.ufc.crateus.eda.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MainView extends Application {

	private Pane pane;
	private Scene scene;
	private ComboBox separateComboBox;
	private Label attentionPathLabel;
	private Label attentionTypeLabel;
	private Label attentionEntLabel;
	private Label nameLabel;
	private Label sepaLabel;
	private TextField pahtTextField;
	private Button exitButton;
	private Button enterButton;

	private ImageView pictureConfigurationImageView;
	private ImageView pictureMenuImageView;
	private ImageView pictureconfiguration2ImageView;
	private Label nameEntLabel;
	private TextField nameEntTextField;

	private static final String[] list = { "SeparateChainingHashST", "SeparateTreeHash", "LinearProbingHasST" };
	ObservableList<String> optionsObservableList;

	private void initComponents() {

		nameLabel = new Label("Caminho dos Arquivos: ");
		nameLabel.setFont(new Font(12));
		pahtTextField = new TextField();
		nameEntLabel = new Label("Nome do arquivo de Entrada: ");
		nameEntLabel.setFont(new Font(12));

		pahtTextField.setPromptText("Ex: D://Documentos//");
		pahtTextField.setFocusTraversable(false);
		pahtTextField.setPrefSize(210, 20);
		sepaLabel = new Label();
		sepaLabel.setText("Opção: ");
		sepaLabel.setFont(new Font(12));

		nameEntTextField = new TextField();
		nameEntTextField.setPromptText("Ex: Entrada.txt");
		nameEntTextField.setPrefSize(200, 20);
		separateComboBox = new ComboBox();
		optionsObservableList = FXCollections.observableArrayList(list);
		separateComboBox.setItems(optionsObservableList);
		separateComboBox.setPrefSize(210, 20);
		exitButton = new Button("Sair");
		exitButton.setPrefSize(80, 40);
		exitButton.setStyle("-fx-cursor: hand;");
		enterButton = new Button("Entrar");
		enterButton.setPrefSize(80, 40);
		enterButton.setStyle("-fx-cursor: hand;");
		attentionEntLabel = new Label();
		attentionPathLabel = new Label();
		attentionTypeLabel = new Label();

		pictureConfigurationImageView = new ImageView(new Image("/br/ufc/crateus/eda/work/photo/Configuration.png"));
		pictureMenuImageView = new ImageView(new Image("/br/ufc/crateus/eda/work/photo/Menu.png"));
		pictureconfiguration2ImageView = new ImageView(new Image("/br/ufc/crateus/eda/work/photo/Configura.png"));
		pane = new AnchorPane();
		scene = new Scene(pane, 480, 440);
	}

	private void initLayout() {
		pictureconfiguration2ImageView.setLayoutX(80);
		pictureconfiguration2ImageView.setLayoutY(40);

		pictureMenuImageView.setLayoutX(20);
		pictureMenuImageView.setLayoutY(250);

		pictureConfigurationImageView.setLayoutX(400);
		pictureConfigurationImageView.setLayoutY(10);

		nameLabel.setLayoutX(60);
		nameLabel.setLayoutY(110);

		pahtTextField.setLayoutX(200);
		pahtTextField.setLayoutY(110);

		attentionPathLabel.setLayoutX(204);
		attentionPathLabel.setLayoutY(140);

		nameEntLabel.setLayoutX(45);
		nameEntLabel.setLayoutY(170);

		nameEntTextField.setLayoutX(210);
		nameEntTextField.setLayoutY(170);

		attentionEntLabel.setLayoutX(200);
		attentionEntLabel.setLayoutY(198);

		sepaLabel.setLayoutX(148);
		sepaLabel.setLayoutY(240);

		separateComboBox.setLayoutX(200);
		separateComboBox.setLayoutY(240);

		attentionTypeLabel.setLayoutX(200);
		attentionTypeLabel.setLayoutY(270);

		exitButton.setLayoutX(240);
		exitButton.setLayoutY(310);

		enterButton.setLayoutX(330);
		enterButton.setLayoutY(310);
	}

	private void initListeners() {

		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});

		enterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String path = pahtTextField.getText();
				String type = (String) separateComboBox.valueProperty().get();
				String name = nameEntTextField.getText();
				if (path != null && type != null && name != null) {
					try {
						boolean teste = validate(path, name);						
						if (teste) {
							SearchView searchView = new SearchView();
							if (type.equals("SeparateChainingHashST")) {
								searchView.start(new Stage(), 1, path, name);
							} else if (type.equals("LinearProbingHasST")) {
								searchView.start(new Stage(), 2, path, name);
							} else {
								searchView.start(new Stage(), 3, path, name);
							}
						} else {
							Erro("Por gentileza digite um caminho válido", AlertType.ERROR, "Atenção");
						}

					} catch (IOException e) {
						System.err.println(e.getMessage());
					}

				} else {					
					attention(path, type, name);
				}
			}

		});

		pahtTextField.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				color(4);
			}
		});

		nameEntTextField.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				color(4);
			}
		});

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initComponents();
		initListeners();
		pane.getChildren().addAll(nameLabel, pahtTextField, sepaLabel, separateComboBox, exitButton, enterButton,
				attentionPathLabel, attentionTypeLabel, nameEntLabel, nameEntTextField, attentionEntLabel,
				pictureConfigurationImageView, pictureMenuImageView, pictureconfiguration2ImageView);
		pane.setStyle("-fx-background-color: #FFFFFF");
		attentionPathLabel.setStyle("-fx-text-fill: red");
		attentionTypeLabel.setStyle("-fx-text-fill: red");
		attentionEntLabel.setStyle("-fx-text-fill: red");

		primaryStage.setScene(scene);
		primaryStage.setTitle("Menu Principal");
		primaryStage.getIcons().add(new Image("/br/ufc/crateus/eda/work/photo/caçador.png"));
		primaryStage.setResizable(false);
		primaryStage.setMaximized(false);
		primaryStage.show();
		initLayout();
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	private Boolean validate(String path, String name) throws IOException {
		File file = new File(path + name);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));
			reader.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	private void Erro(String er, AlertType type, String Header) {
		Alert alert = new Alert(type);
		alert.setHeaderText(Header);
		alert.setContentText(er);
		alert.showAndWait();
	}

	private void attention(String type, String path, String name) {
		if (path == null) {
			attentionPathLabel.setText("Atenção Digite um caminho correto");
			color(1);
		}
		if (type.equals("")) {
			attentionTypeLabel.setText("Atenção selecione uma das três opções");
			color(2);
		}
		if (name.equals("")) {
			attentionEntLabel.setText("Atenção digite o nome do arquivo correto .txt");
			color(3);
		}
	}

	private void color(int op) {

		DropShadow dropShadow = new DropShadow();
		dropShadow.setSpread(0.5);
		dropShadow.setColor(Color.RED);

		if (op == 1) {
			pahtTextField.setEffect(dropShadow);
		}
		if (op == 2) {
			separateComboBox.setEffect(dropShadow);
		}
		if (op == 3) {
			nameEntTextField.setEffect(dropShadow);
		}
		if (op == 4) {
			pahtTextField.setEffect(null);
			separateComboBox.setEffect(null);
			nameEntTextField.setEffect(null);

			attentionPathLabel.setText("");
			attentionTypeLabel.setText("");
			attentionEntLabel.setText("");
		}
	}
}
