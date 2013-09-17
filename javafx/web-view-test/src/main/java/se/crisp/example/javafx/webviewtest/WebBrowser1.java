package se.crisp.example.javafx.webviewtest;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowser1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createRoot(stage), 500, 450);

        stage.setTitle("Web browser 1");
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createRoot(Stage stage) {
        StackPane root = new StackPane();
        root.getChildren().add(createWebView(stage));
        return root;
    }

    private WebView createWebView(Stage stage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://localhost:8080/authorization?response_type=code" +
                "&client_id=fake" +
                "&scope=SCOPE" +
                "&state=STATE" +
                "&redirect_uri=http://localhost:8080/landing");
        webEngine.getLoadWorker().stateProperty().addListener(getListener(stage, webEngine));
        return webView;
    }

    private ChangeListener<? super Worker.State> getListener(Stage stage, WebEngine webEngine) {
        return (ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                stage.setTitle(webEngine.titleProperty().get());
            }
            if (newState == Worker.State.FAILED) {
                stage.setTitle("FAILED");
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
