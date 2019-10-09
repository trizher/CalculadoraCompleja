package dad.javafx.calculadoracompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraApp extends Application {
	private Complejo operando1 = new Complejo();
	private Complejo operando2 = new Complejo();
	private Complejo resultado = new Complejo();
	private StringProperty operador = new SimpleStringProperty();
	
	private TextField operando11Text;
	private TextField operando12Text;
	private TextField operando21Text;
	private TextField operando22Text;
	private TextField resultado1Text;
	private TextField resultado2Text;
	private ComboBox<String> operadorCombo;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		operando11Text = new TextField();
		
		operando12Text = new TextField();
		
		operando21Text = new TextField();
		
		operando22Text = new TextField();
		
		resultado1Text = new TextField();
		resultado1Text.setDisable(true);
		
		resultado2Text = new TextField();
		resultado2Text.setDisable(true);
		
		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+","-","*","/");
		
		HBox hbox1 = new HBox(5, operando11Text, new Label(" + "), operando12Text, new Label("i"));
		hbox1.setAlignment(Pos.CENTER);
		HBox hbox2 = new HBox(5, operando21Text, new Label(" + "), operando22Text, new Label("i"));
		hbox2.setAlignment(Pos.CENTER);
		HBox hbox3 = new HBox(5, resultado1Text, new Label(" + "), resultado2Text, new Label("i"));
		hbox3.setAlignment(Pos.CENTER);
		VBox vbox = new VBox(5, hbox1, hbox2, new Separator(), hbox3);
		vbox.setAlignment(Pos.CENTER);
		HBox root = new HBox(15, operadorCombo, vbox);
		root.setAlignment(Pos.CENTER);
		
		Bindings.bindBidirectional(operando11Text.textProperty(), operando1.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando12Text.textProperty(), operando1.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando21Text.textProperty(), operando2.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando22Text.textProperty(), operando2.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultado1Text.textProperty(), resultado.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultado2Text.textProperty(), resultado.imaginarioProperty(), new NumberStringConverter());
		operador.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Calculadora");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		operador.addListener((o, ov, nv) -> onOperadorChanged(nv));
		operadorCombo.getSelectionModel().selectFirst();
		
		
	}

	private void onOperadorChanged(String nv) {
		switch(nv) {
		case "+":
			resultado.realProperty().bind(operando1.realProperty().add(operando2.realProperty()));
			resultado.imaginarioProperty().bind(operando1.imaginarioProperty().add(operando2.imaginarioProperty()));
			break;
		case "-":
			resultado.realProperty().bind(operando1.realProperty().subtract(operando2.realProperty()));
			resultado.imaginarioProperty().bind(operando1.imaginarioProperty().subtract(operando2.imaginarioProperty()));
			break;
		case "*":
			resultado.realProperty().bind(operando1.realProperty().multiply(operando2.realProperty()).subtract(operando1.imaginarioProperty().multiply(operando2.imaginarioProperty())));
			resultado.imaginarioProperty().bind(operando1.realProperty().multiply(operando2.imaginarioProperty()).add(operando1.imaginarioProperty().multiply(operando2.realProperty())));
			break;
		case "/":
			resultado.realProperty().bind(operando1.realProperty().multiply(operando2.realProperty()).add(operando1.imaginarioProperty().multiply(operando2.imaginarioProperty()))
					.divide(operando2.realProperty().multiply(operando2.realProperty().add(operando2.imaginarioProperty().multiply(operando2.imaginarioProperty())))));
			resultado.imaginarioProperty().bind(operando1.imaginarioProperty().multiply(operando2.realProperty().subtract(operando1.realProperty().multiply(operando2.imaginarioProperty())))
					.divide(operando2.realProperty().multiply(operando2.realProperty().add(operando2.imaginarioProperty().multiply(operando2.imaginarioProperty())))));
			break;
		}
	}


	public static void main(String[] args) {
		launch();

	}

}
