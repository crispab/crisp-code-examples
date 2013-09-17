package se.crisp.example.javafx.webviewtest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowser1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createRoot(), 500, 450);

        stage.setTitle("Web browser 1");
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createRoot() {
        StackPane root = new StackPane();
        root.getChildren().add(createWebView());
        return root;
    }

    private WebView createWebView() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://crisp.se");
        return webView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
