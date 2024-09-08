package com.npc.common.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

/**
 * @author NPC
 * @description
 * @create 2024/7/28 9:45
 */
public class TxtToClipboard extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField textField = new TextField();
        textField.setText("要复制的文本");

        textField.setOnAction(event -> {
            String text = textField.getText();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            clipboard.setContent(content);
        });

        Scene scene = new Scene(textField, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
