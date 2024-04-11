package com.example.lesothotriviagame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LesothoTrivia extends Application {

    private int score = 0;
    private int questionIndex = 0;

    private final Question[] questions = {
            new Question("moshweshwe ke mang?", "D) Rabasotho(Correct)", new String[]{"A) mothehi oa sechaba sa basotho", "B) Basotho bohle", "C) RabasothoII", "D) Mount Moorosi "}, "images/2.jpg"),
            new Question("katiba oa basotho ke?", "C) Mokorotlo (Correct)", new String[]{"A) Moshoeshoe", "B) Sesotho", "C) Mokorotlo ", "D) Maloti"}, "images/1.jpg"),
            new Question("What is the capital city of Lesotho?", "A) Maseru (Correct)", new String[]{"A) Maseru ", "B) Roma", "C) Teyateyaneng", "D) Mafeteng"}, "images/5.jpg"),
            new Question("Re arotswe le Afrika borwa ke?", "B) mohokare (Correct)", new String[]{"A) Caledon River", "B) mohokare(Correct)", "C) Limpopo River", "D) Vaal River"}, "images/3.jpg"),
            new Question("Letsie le teng ke la nomoro mang?", "D) 3 (Correct)", new String[]{"A) 1", "B) 2", "C) 3", "D) 4"}, "images/2.jpg")
    };

    private final ToggleGroup choicesGroup = new ToggleGroup();

    @Override
    public void start(Stage stage) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #ADD8E6;"); // Lesotho-themed color

        displayQuestion(mainLayout);

        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Lesotho Trivia");
        stage.show();
    }

    private void displayQuestion(VBox mainLayout) {
        mainLayout.getChildren().clear();

        Label questionLabel = new Label(questions[questionIndex].getQuestionText());
        questionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        questionLabel.setTextFill(Color.WHITE);

        // Add image
        String imagePath = questions[questionIndex].getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
            mainLayout.getChildren().add(imageView);
        }

        VBox choicesLayout = new VBox(10);
        RadioButton[] radioButtons = new RadioButton[questions[questionIndex].getChoices().length];
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i] = new RadioButton(questions[questionIndex].getChoices()[i]);
            radioButtons[i].setToggleGroup(choicesGroup);
            radioButtons[i].setFont(Font.font("Arial", 14));
            radioButtons[i].setTextFill(Color.WHITE);
            choicesLayout.getChildren().add(radioButtons[i]);
        }

        mainLayout.getChildren().addAll(questionLabel, choicesLayout);

        Button submitButton = new Button("Submit Answer");
        submitButton.setStyle("-fx-background-color: #008000; -fx-text-fill: white;");
        submitButton.setOnAction(event -> {
            RadioButton selectedButton = (RadioButton) choicesGroup.getSelectedToggle();
            if (selectedButton != null) {
                String selectedAnswer = selectedButton.getText();
                boolean isCorrect = selectedAnswer.equals(questions[questionIndex].getCorrectAnswer());

                // Show feedback
                Alert feedbackAlert = new Alert(Alert.AlertType.INFORMATION);
                feedbackAlert.setTitle("Answer Feedback");
                feedbackAlert.setHeaderText(null);
                if (isCorrect) {
                    feedbackAlert.setContentText("Correct! Well done.");
                    score++;
                } else {
                    feedbackAlert.setContentText("Incorrect. The correct answer is: " + questions[questionIndex].getCorrectAnswer());
                }
                feedbackAlert.showAndWait();

                questionIndex++;
                if (questionIndex < questions.length) {
                    displayQuestion(mainLayout);
                } else {
                    showResults(mainLayout);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Answer Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select an answer.");
                alert.showAndWait();
            }
        });

        mainLayout.getChildren().add(submitButton);
    }

    private void showResults(VBox mainLayout) {
        mainLayout.getChildren().clear();
        Label resultLabel = new Label("WOW THANK YOU FOR PLAYING LESOTHO HOME GAME\nYour score: " + score + "/" + questions.length);
        resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        resultLabel.setTextFill(Color.WHITE);
        mainLayout.getChildren().add(resultLabel);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Question {
    private final String questionText;
    private final String correctAnswer;
    private final String[] choices;
    private final String imagePath;

    public Question(String questionText, String correctAnswer, String[] choices, String imagePath) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
        this.imagePath = imagePath;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getImagePath() {
        return imagePath;
    }
}
