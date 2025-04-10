package org.example.recipevault;

/**
 * Recipe Vault Application
 *
 * This JavaFX application provides a user interface to enter recipe information.
 * Users can:
 *  - Input recipe metadata (name, category, author, time, servings, theme)
 *  - Add and remove up to 15 ingredients
 *  - Add and remove up to 25 cooking instruction steps
 *  - Enter notes or tips for the recipe
 *  - Reset the form to clear all inputs
 *  - Receive validation alerts via pop-up dialogs
 *
 * The application window opens centered horizontally and maximized vertically.
 * Lists for ingredients and instructions use ObservableLists linked to ListView components.
 *
 * Developed using JavaFX UI components like BorderPane, GridPane, ListView, TextField, and TextArea.
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Main JavaFX application class for Recipe Vault
public class Main extends Application {
    // Recipe attributes
    private TextField recipeNameField;
    private Label recipeNameCount;
    private int recipeNameLimit = 13;
    private TextField recipeCategoryField;
    private Label recipeCategoryCount;
    private int recipeCategoryLimit = 9;
    private TextField prepTimeField;
    private Label prepTimeCount;
    private int prepTimeLimit = 9;
    private TextField cookTimeField;
    private Label cookTimeCount;
    private int cookTimeLimit = 9;
    private TextField totalTimeField;
    private Label totalTimeCount;
    private int totalTimeLimit = 9;
    private TextField servingsField;
    private Label servingsCount;
    private int servingsLimit = 9;
    private TextField recipeAuthorField;
    private Label recipeAuthorCount;
    private int recipeAuthorLimit = 16;
    private ComboBox<String> themeComboBox;

    // Ingredients
    private TextField ingredientField;
    private Label ingredientCount;
    private int ingredientLimit = 20;
    private ListView<String> ingredientListView;
    private ObservableList<String> ingredientsList = FXCollections.observableArrayList();

    // Instructions
    private TextField instructionField;
    private Label instructionCount;
    private int instructionLimit = 49;
    private ListView<String> instructionListView;
    private ObservableList<String> instructionsList = FXCollections.observableArrayList();

    // Notes
    private TextArea notesArea;

    // Status label
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recipe Vault");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        VBox headerBox = createHeader();
        mainLayout.setTop(headerBox);

        GridPane contentGrid = createContentGrid();
        mainLayout.setCenter(contentGrid);

        HBox buttonBox = createButtons(primaryStage);
        mainLayout.setBottom(buttonBox);

        Scene scene = new Scene(mainLayout, 800, javafx.stage.Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.centerOnScreen();
        primaryStage.setOnShown(e -> primaryStage.setX((javafx.stage.Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2));
        primaryStage.setY(0);
        primaryStage.setScene(scene);
        primaryStage.setHeight(javafx.stage.Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Creates the top header section with title and slogan
    private VBox createHeader() {
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));

        Label titleLabel = new Label("Recipe Vault");
        titleLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 24));

        Label sloganLabel = new Label("Preserve the past, cook for the future!");
        sloganLabel.setFont(Font.font("Georgia", FontWeight.NORMAL, 14));

        headerBox.getChildren().addAll(titleLabel, sloganLabel);
        return headerBox;
    }

    // Creates the main content area including form fields, ingredient and instruction lists
    private GridPane createContentGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        int row = 0;

        grid.add(new Label("Recipe Name:"), 0, row);
        recipeNameField = new TextField();
        HBox recipeNameBox = new HBox(10);

        recipeNameField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= recipeNameLimit) {
                return change;
            } else {
                return null;
            }
        }));

        recipeNameCount = new Label("0/" + recipeNameLimit);
        recipeNameBox.getChildren().addAll(recipeNameField, recipeNameCount);
        grid.add(recipeNameBox, 1, row);

        // Bind the text content
        recipeNameCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = recipeNameField.getText().length();
            return length + "/" + recipeNameLimit;

        }, recipeNameField.textProperty()));

        // Add a listener to update the color if recipeNameField character limit reached
        recipeNameField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            recipeNameCount.setTextFill(length == recipeNameLimit ? Color.RED : Color.GRAY);
        });

        grid.add(recipeNameField, 1, row);
        grid.add(recipeNameCount, 2, row);

        row++;
        grid.add(new Label("Category/Cuisine:"), 0, row);
        recipeCategoryField = new TextField();
        recipeCategoryCount = new Label("0/" + recipeCategoryLimit);

        // Bind the text content
        recipeCategoryCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = recipeCategoryField.getText().length();
            return length + "/" + recipeCategoryLimit;

        }, recipeCategoryField.textProperty()));

        // Add a listener to update the color if ingredientField character limit reached
        recipeCategoryField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            recipeCategoryCount.setTextFill(length == recipeCategoryLimit ? Color.RED : Color.GRAY);
        });

        recipeCategoryField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= recipeCategoryLimit) {
                return change;
            } else {
                return null;
            }
        }));



        grid.add(recipeCategoryField, 1, row);
        grid.add(recipeCategoryCount, 2, row);

        row++;
        grid.add(new Label("Recipe Author:"), 0, row);
        recipeAuthorCount = new Label("0/" + recipeAuthorLimit);
        recipeAuthorField = new TextField();

        // Bind the text content
        recipeAuthorCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = recipeAuthorField.getText().length();
            return length + "/" + recipeAuthorLimit;

        }, recipeAuthorField.textProperty()));

        // Add a listener to update the color if recipeAuthorField character limit reached
        recipeAuthorField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            recipeAuthorCount.setTextFill(length == recipeAuthorLimit ? Color.RED : Color.GRAY);
        });

        recipeAuthorField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= recipeAuthorLimit) {
                return change;
            } else {
                return null;
            }
        }));


        grid.add(recipeAuthorField, 1, row);
        grid.add(recipeAuthorCount, 2, row);

        row++;
        grid.add(new Label("Prep Time:"), 0, row);
        prepTimeCount = new Label("0/" + prepTimeLimit);
        prepTimeField = new TextField();

        // Bind the text content
        prepTimeCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = prepTimeField.getText().length();
            return length + "/" + prepTimeLimit;

        }, prepTimeField.textProperty()));

        // Add a listener to update the color if prepTimeField character limit reached
        prepTimeField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            prepTimeCount.setTextFill(length == prepTimeLimit ? Color.RED : Color.GRAY);
        });

        prepTimeField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= prepTimeLimit) {
                return change;
            } else {
                return null;
            }
        }));

        grid.add(prepTimeField, 1, row);
        grid.add(prepTimeCount, 2, row);

        row++;
        grid.add(new Label("Cook Time:"), 0, row);
        cookTimeCount = new Label("0/" + cookTimeLimit);
        cookTimeField = new TextField();

        // Bind the text content
        cookTimeCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = cookTimeField.getText().length();
            return length + "/" + cookTimeLimit;

        }, cookTimeField.textProperty()));

        // Add a listener to update the color if cookTimeField character limit reached
        cookTimeField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            cookTimeCount.setTextFill(length == cookTimeLimit ? Color.RED : Color.GRAY);
        });

        cookTimeField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= cookTimeLimit) {
                return change;
            } else {
                return null;
            }
        }));

        grid.add(cookTimeField, 1, row);
        grid.add(cookTimeCount, 2, row);

        row++;
        grid.add(new Label("Total Time:"), 0, row);
        totalTimeCount = new Label("0/" + totalTimeLimit);
        totalTimeField = new TextField();

        // Bind the text content
        totalTimeCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = totalTimeField.getText().length();
            return length + "/" + totalTimeLimit;

        }, totalTimeField.textProperty()));

        // Add a listener to update the color if totalTimeField character limit reached
        totalTimeField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            totalTimeCount.setTextFill(length == totalTimeLimit ? Color.RED : Color.GRAY);
        });

        totalTimeField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= totalTimeLimit) {
                return change;
            } else {
                return null;
            }
        }));

        grid.add(totalTimeField, 1, row);
        grid.add(totalTimeCount, 2, row);

        row++;
        grid.add(new Label("Servings:"), 0, row);
        servingsCount = new Label("0/" + servingsLimit);
        servingsField = new TextField();

        // Bind the text content
        servingsCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = servingsField.getText().length();
            return length + "/" + servingsLimit;

        }, servingsField.textProperty()));

        // Add a listener to update the color if servingsField character limit reached
        servingsField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            servingsCount.setTextFill(length == servingsLimit ? Color.RED : Color.GRAY);
        });

        servingsField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= servingsLimit) {
                return change;
            } else {
                return null;
            }
        }));

        grid.add(servingsField, 1, row);
        grid.add(servingsCount, 2, row);

        row++;
        grid.add(new Label("Theme:"), 0, row);
        themeComboBox = new ComboBox<>(FXCollections.observableArrayList(
                "Spring", "Summer", "Fall", "Winter"
        ));
        themeComboBox.setPromptText("Select theme");
        themeComboBox.setEditable(false);
        grid.add(themeComboBox, 1, row);

        row++;
        grid.add(new Separator(), 0, row, 2, 1);

        row++;
        grid.add(new Label("Ingredients"), 0, row, 2, 1);

        row++;
        grid.add(new Label("Add Ingredient:"), 0, row);
        ingredientCount = new Label("0/" + ingredientLimit);
        ingredientField = new TextField();

        // Bind the text content
        ingredientCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = ingredientField.getText().length();
            return length + "/" + ingredientLimit;

        }, ingredientField.textProperty()));

        // Add a listener to update the color if ingredientField character limit reached
        ingredientField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            ingredientCount.setTextFill(length == ingredientLimit ? Color.RED : Color.GRAY);
        });

        ingredientField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= ingredientLimit) {
                return change;
            } else {
                return null;
            }
        }));

        /*ingredientField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 20) ingredientField.setText(oldVal);
        });*/

        Button addIngredientButton = new Button("Add");
        addIngredientButton.setOnAction(e -> addIngredient());
        HBox ingredientBox = new HBox(10, ingredientField, addIngredientButton);
        grid.add(ingredientBox, 1, row);
        grid.add(ingredientCount, 2, row);

        // Add support for pressing Enter in ingredientField
        ingredientField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String ingredient = ingredientField.getText();
                if ((!ingredient.isEmpty() && (ingredientsList.size() < 15))) {
                    ingredientsList.add(ingredient);
                    ingredientField.clear();
                }
                else if (ingredientsList.size() >= 15) {
                    showMessageDialog("You can only add up to 15 ingredients.");
                }
            }
        });



        row++;
        grid.add(new Label("Ingredient List:"), 0, row);
        ingredientListView = new ListView<>(ingredientsList);
        ingredientListView.setPrefHeight(400);
        grid.add(ingredientListView, 1, row);

        row++;
        Button removeIngredientButton = new Button("Remove Selected Ingredient");
        removeIngredientButton.setOnAction(e -> removeIngredient());
        grid.add(removeIngredientButton, 1, row);


        row++;
        grid.add(new Separator(), 0, row, 2, 1);

        row++;
        grid.add(new Label("Cooking Instructions"), 0, row, 2, 1);

        row++;
        grid.add(new Label("Add Instruction:"), 0, row);
        instructionField = new TextField();
        instructionCount = new Label("0/" + instructionLimit);

        // Bind the text content
        instructionCount.textProperty().bind(Bindings.createStringBinding(() -> {
            int length = instructionField.getText().length();
            return length + "/" + instructionLimit;

        }, instructionField.textProperty()));

        // Add a listener to update the color if instructionField character limit reached
        instructionField.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            instructionCount.setTextFill(length == instructionLimit ? Color.RED : Color.GRAY);
        });

        instructionField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= instructionLimit) {
                return change;
            } else {
                return null;
            }
        }));

        instructionField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 100) instructionField.setText(oldVal);
        });
        Button addInstructionButton = new Button("Add");
        addInstructionButton.setOnAction(e -> {
            addInstruction();
            GridPane.setRowIndex(statusLabel, GridPane.getRowIndex(instructionListView) - 1);
        });

        // Add support for pressing Enter in instructionField
        instructionField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String step = instructionField.getText();
                if (!step.isEmpty() && (instructionsList.size() < 25)) {
                    instructionsList.add(step);
                    instructionField.clear();
                }
                else if(instructionsList.size() >= 25) {
                    showMessageDialog("You can only add up to 25 steps.");
                }
            }
        });

        HBox instructionBox = new HBox(10, instructionField, addInstructionButton);
        grid.add(instructionBox, 1, row);
        grid.add(instructionCount, 2, row);

        row++;
        grid.add(new Label("Instruction List:"), 0, row);
        instructionListView = new ListView<>(instructionsList);
        instructionListView.setPrefHeight(400);

        grid.add(instructionListView, 1, row);

        row++;
        Button removeInstructionButton = new Button("Remove Selected Instruction");
        removeInstructionButton.setOnAction(e -> removeInstruction());
        grid.add(removeInstructionButton, 1, row);

        row++;
        grid.add(new Separator(), 0, row, 2, 1);

        row++;
        grid.add(new Label("Notes/Tips"), 0, row, 2, 1);

        row++;
        notesArea = new TextArea();
        notesArea.setPrefHeight(400);

        notesArea.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= 230) {
                return change;
            } else {
                return null;
            }
        }));

        grid.add(notesArea, 1, row);

        return grid;
    }

    // Creates the bottom row of buttons (Save, Reset, Close)
    private HBox createButtons(Stage stage) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(20, 0, 0, 0));

        Button save = new Button("Save");

        //Save user input to pdf if all fields are field or selected besides notes.
        save.setOnAction(e -> {
            if(validateFields())
            {
                saveRecipeToPDF();
            }

        });
        Button reset = new Button("Reset");
        reset.setOnAction(e -> resetForm());
        Button close = new Button("Close");
        close.setOnAction(e -> stage.close());
        box.getChildren().addAll(save, reset, close);
        return box;
    }

    // Validates if all required fields are filled
    private boolean validateFields() {
        List<String> emptyFields = new ArrayList<>();
        if (recipeNameField.getText().trim().isEmpty()) {
            emptyFields.add("Recipe Name");
        }
        if (recipeCategoryField.getText().trim().isEmpty()) {
            emptyFields.add("Category/Cuisine");
        }
        if (recipeAuthorField.getText().trim().isEmpty()) {
            emptyFields.add("Recipe Author");
        }
        if (prepTimeField.getText().trim().isEmpty()) {
            emptyFields.add("Prep Time");
        }
        if (cookTimeField.getText().trim().isEmpty()) {
            emptyFields.add("Cook Time");
        }
        if (totalTimeField.getText().trim().isEmpty()) {
            emptyFields.add("Total Time");
        }
        if (servingsField.getText().trim().isEmpty()) {
            emptyFields.add("Servings");
        }
        if (themeComboBox.getValue() == null) {
            emptyFields.add("Theme");
        }
        if (ingredientsList.isEmpty()) {
            emptyFields.add("Ingredients List");
        }
        if (instructionsList.isEmpty()) {
            emptyFields.add("Cooking Instructions");
        }
        if (!emptyFields.isEmpty()) {
            showMessageDialog("The following fields cannot be empty: " + String.join(", ", emptyFields));
            return false;
        }

        return true;
    }

    // Adds an ingredient to the list if valid and under limit
    private void addIngredient() {
        String ingredient = ingredientField.getText().trim();
        if (ingredientsList.size() >= 15) {
            showMessageDialog("You can only add up to 15 ingredients.");
            return;
        }
        if (!ingredient.isEmpty() && !ingredientsList.contains(ingredient)) {
            ingredientsList.add(ingredient);
            ingredientField.clear();
        } else {
            showMessageDialog("Ingredient already in the list or empty");
        }
        statusLabel.setText("Ingredient already in the list or empty");
    }

    // Removes the selected ingredient from the list
    private void removeIngredient() {
        String selected = ingredientListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ingredientsList.remove(selected);
            showMessageDialog("Ingredient '" + selected + "' removed");
        } else {
            showMessageDialog("Select an ingredient to remove");
        }
    }

    // Adds an instruction step to the list if valid and under limit
    private void addInstruction() {
        String step = instructionField.getText().trim();
        if (instructionsList.size() >= 25) {
            showMessageDialog("You can only add up to 25 instruction steps.");
            return;
        }
        if (!step.isEmpty() && !instructionsList.contains(step)) {
            instructionsList.add(step);
            instructionField.clear();
        } else {
            showMessageDialog("Instruction already in the list or empty");
        }
        statusLabel.setText("Instruction already in the list or empty");
    }

    // Removes the selected instruction from the list
    private void removeInstruction() {
        String selected = instructionListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            instructionsList.remove(selected);
            showMessageDialog("Instruction removed");
        } else {
            showMessageDialog("Select an instruction to remove");
        }
    }

    // Shows a popup dialog with a given message
    private void showMessageDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recipe Vault");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveRecipeToPDF() {
        String name = recipeNameField.getText();
        String category = recipeCategoryField.getText();
        String author = recipeAuthorField.getText();
        String prepTime = prepTimeField.getText();
        String cookTime = cookTimeField.getText();
        String totalTime = totalTimeField.getText();
        String servings = servingsField.getText();
        String theme = themeComboBox.getValue();
        List<String> ingredients = new ArrayList<>(ingredientsList);
        List<String> instructions = new ArrayList<>(instructionsList);
        String notes = notesArea.getText();

        RecipePDFWriter.saveRecipeToPDF(name, category, author, prepTime, cookTime, totalTime, servings, theme, ingredients, instructions, notes);
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Resets all input fields and clears ingredient and instruction lists
    private void resetForm() {
        recipeNameField.clear();
        recipeCategoryField.clear();
        prepTimeField.clear();
        cookTimeField.clear();
        totalTimeField.clear();
        servingsField.clear();
        recipeAuthorField.clear();
        themeComboBox.getEditor().clear();
        themeComboBox.setValue(null);

        ingredientField.clear();
        ingredientsList.clear();

        instructionField.clear();
        instructionsList.clear();

        notesArea.clear();
    }
}
