package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    // Recipe attributes
    private TextField recipeNameField;
    private TextField recipeCategoryField;
    private TextField prepTimeField;
    private TextField cookTimeField;
    private TextField totalTimeField;
    private TextField servingsField;
    private TextField recipeAuthorField;
    private ComboBox<String> themeComboBox;
    
    // Ingredients
    private TextField ingredientField;
    private TextArea ingredientListArea;
    private Map<String, String> ingredientsMap = new HashMap<>();
    
    // Instructions and Notes
    private TextArea instructionsArea;
    private TextArea notesArea;
    
    // Status label
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recipe Vault");
        
        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        
        // Header
        VBox headerBox = createHeader();
        mainLayout.setTop(headerBox);
        
        // Center content
        GridPane gridPane = createContentGrid();
        mainLayout.setCenter(gridPane);
        
        // Bottom buttons
        HBox buttonBox = createButtons(primaryStage);
        mainLayout.setBottom(buttonBox);
        
        // Set the scene
        Scene scene = new Scene(mainLayout, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

    private GridPane createContentGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        
        // Recipe basic info section
        int row = 0;
        
        // Recipe Name
        grid.add(new Label("Recipe Name:"), 0, row);
        recipeNameField = new TextField();
        recipeNameField.setPromptText("Enter recipe name");
        grid.add(recipeNameField, 1, row);
        
        // Recipe Category
		row++;
		grid.add(new Label("Category/Cuisine:"), 0, row);
		recipeCategoryField = new TextField();
		recipeCategoryField.setPromptText("Enter category or cuisine");
		grid.add(recipeCategoryField, 1, row);

        // Recipe Author
        row++;
        grid.add(new Label("Recipe Author:"), 0, row);
        recipeAuthorField = new TextField();
        recipeAuthorField.setPromptText("Enter author name");
        grid.add(recipeAuthorField, 1, row);
        
        // Prep Time
        row++;
        grid.add(new Label("Prep Time:"), 0, row);
        prepTimeField = new TextField();
        prepTimeField.setPromptText("e.g., 15 minutes");
        grid.add(prepTimeField, 1, row);
        
        // Cook Time
        row++;
        grid.add(new Label("Cook Time:"), 0, row);
        cookTimeField = new TextField();
        cookTimeField.setPromptText("e.g., 30 minutes");
        grid.add(cookTimeField, 1, row);
        
        // Total Time
        row++;
        grid.add(new Label("Total Time:"), 0, row);
        totalTimeField = new TextField();
        totalTimeField.setPromptText("e.g., 45 minutes");
        grid.add(totalTimeField, 1, row);
        
        // Servings
        row++;
        grid.add(new Label("Servings:"), 0, row);
        servingsField = new TextField();
        servingsField.setPromptText("e.g., 4 servings");
        grid.add(servingsField, 1, row);
        
        // Theme
        row++;
        grid.add(new Label("Theme:"), 0, row);
        themeComboBox = new ComboBox<>(FXCollections.observableArrayList(
                "Everyday", "Holiday", "Special Occasion", "Quick & Easy", "Budget Friendly", 
                "Healthy", "Comfort Food", "Kid Friendly", "Other"
        ));
        themeComboBox.setPromptText("Select theme");
        themeComboBox.setEditable(true);
        grid.add(themeComboBox, 1, row);
        
        // Ingredients section
        row++;
        Separator separator = new Separator();
        grid.add(separator, 0, row, 2, 1);
        
        // Ingredients heading
        row++;
        Label ingredientsLabel = new Label("Ingredients");
        ingredientsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid.add(ingredientsLabel, 0, row, 2, 1);
        
        // Ingredient input
        row++;
        grid.add(new Label("Add Ingredient:"), 0, row);
        
        HBox ingredientInputBox = new HBox(10);
        ingredientField = new TextField();
        ingredientField.setPromptText("Enter ingredient (max 20 chars)");
        ingredientField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20) {
                ingredientField.setText(oldValue);
            }
        });
        
        Button addIngredientButton = new Button("Add");
        addIngredientButton.setOnAction(e -> addIngredient());
        
        ingredientInputBox.getChildren().addAll(ingredientField, addIngredientButton);
        grid.add(ingredientInputBox, 1, row);
        
        // Status label for ingredient action feedback
        row++;
        statusLabel = new Label("");
        grid.add(statusLabel, 1, row);
        
        // Ingredient list
        row++;
        grid.add(new Label("Ingredient List:"), 0, row);
        ingredientListArea = new TextArea();
        ingredientListArea.setEditable(false);
        ingredientListArea.setPrefHeight(100);
        grid.add(ingredientListArea, 1, row);
        
        // Remove ingredient button
        row++;
        Button removeIngredientButton = new Button("Remove Selected Ingredient");
        removeIngredientButton.setOnAction(e -> removeIngredient());
        grid.add(removeIngredientButton, 1, row);
        
        // Instructions section
        row++;
        Separator instructionsSeparator = new Separator();
        grid.add(instructionsSeparator, 0, row, 2, 1);
        
        // Instructions heading
        row++;
        Label instructionsLabel = new Label("Cooking Instructions");
        instructionsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid.add(instructionsLabel, 0, row, 2, 1);
        
        // Instructions textarea
        row++;
        grid.add(new Label("Steps:"), 0, row);
        instructionsArea = new TextArea();
        instructionsArea.setPromptText("Enter cooking instructions step by step");
        instructionsArea.setPrefHeight(120);
        grid.add(instructionsArea, 1, row);
        
        // Notes section
        row++;
        Separator notesSeparator = new Separator();
        grid.add(notesSeparator, 0, row, 2, 1);
        
        // Notes heading
        row++;
        Label notesLabel = new Label("Notes/Tips");
        notesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid.add(notesLabel, 0, row, 2, 1);
        
        // Notes textarea
        row++;
        grid.add(new Label("Notes:"), 0, row);
        notesArea = new TextArea();
        notesArea.setPromptText("Enter any additional notes or tips");
        notesArea.setPrefHeight(80);
        grid.add(notesArea, 1, row);
        
        return grid;
    }

    private HBox createButtons(Stage primaryStage) {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> showMessageDialog("Save button clicked!"));
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());
        
        buttonBox.getChildren().addAll(saveButton, closeButton);
        return buttonBox;
    }
    
    private void addIngredient() {
        String ingredient = ingredientField.getText().trim();
        if (!ingredient.isEmpty()) {
            // Add to data structure (Map)
            ingredientsMap.put(ingredient, ingredient);
            
            // Update the TextArea
            updateIngredientList();
            
            // Update status label
            statusLabel.setText("Ingredient '" + ingredient + "' added to the list");
            
            // Clear the input field
            ingredientField.clear();
        }
    }
    
    private void removeIngredient() {
        String selectedText = ingredientListArea.getSelectedText().trim();
        if (!selectedText.isEmpty() && ingredientsMap.containsKey(selectedText)) {
            // Remove from data structure
            ingredientsMap.remove(selectedText);
            
            // Update the TextArea
            updateIngredientList();
            
            // Update status label
            statusLabel.setText("Ingredient '" + selectedText + "' removed from the list");
        } else {
            statusLabel.setText("Please select an ingredient to remove");
        }
    }
    
    private void updateIngredientList() {
        StringBuilder sb = new StringBuilder();
        for (String ingredient : ingredientsMap.keySet()) {
            sb.append(ingredient).append("\n");
        }
        ingredientListArea.setText(sb.toString());
    }
    
    private void showMessageDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recipe Vault");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}