package exceptionWindow;

import dialogs.FxDialogs;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by akram on 10.04.17.
 */
public class ExceptionWindowFX extends Application{
    @Override
    public void start(Stage primaryStage) {

//        // start is called on the FX Application Thread,
//        // so Thread.currentThread() is the FX application thread:
//        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
//            System.out.println("Handler caught exception: "+throwable.getMessage());
//        });
//
//        StackPane root = new StackPane();
//        Button button = new Button("Throw exception");
//        button.setOnAction(event -> {
//            throw new RuntimeException("Boom!") ;
//        });
//        root.getChildren().add(button);
//        Scene scene = new Scene(root, 150, 60);
//        primaryStage.setScene(scene);
//        primaryStage.show();

        FxDialogs.showInformation("Hi", "Good Morning y'all!");
        if (FxDialogs.showConfirm("Choose one baby!", "Can i ask you a question?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
            FxDialogs.showWarning(null, "Pay attention to my next question!");
            String answer = FxDialogs.showTextInput("Are you a pink elephant disguised as a flying pig?", "Tell me!", "No");
            FxDialogs.showError(null, "You should not have said " + answer + "!");
            FxDialogs.showException("Now i'm angry", "I'm going home...", new RuntimeException("Exception caused by angry dinossaurs"));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
