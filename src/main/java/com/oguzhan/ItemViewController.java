package com.oguzhan;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import javafx.scene.layout.VBox;

import java.io.File;

public class ItemViewController extends Pane {
    @FXML
    private TextArea movieNameText;

    @FXML
    private TextField runTimeText;

    @FXML
    private TextField ratingText;

    @FXML
    private TextArea noteText;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchButton;

    @FXML
    private Button removeButton;

    @FXML
    private ImageView poster;

    private ItemView itemView;

    private MainController mainController;


    @FXML
    private void initialize() {
        movieNameText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (itemView != null){
                itemView.setName(newValue);  
            }   
        });
        runTimeText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (itemView != null){
                itemView.setRunTime(newValue);
            }   
        });
        ratingText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (itemView != null){
                itemView.setRating(newValue);
            }   
        });
        noteText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (itemView != null){
                itemView.setNotes(newValue);
            }   
        });
    }

    @FXML
    private void uploadPoster() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files",
        "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // Load the image and set it to the ImageView
            Image image = new Image(file.toURI().toString());
            itemView.setPosterUrl(file.toURI().toString());
            poster.setImage(image);
        }
    }

    @FXML
    private void removeItemView() {
        Pane parent = (Pane) removeButton.getParent();
        if ( itemView != null) {
            VBox itemContainer = (VBox) parent.getParent();
            if ( itemContainer != null ) {
                itemContainer.getChildren().remove(parent);
                mainController.getPageContents().get(itemView.getPage()).remove(itemView);
            }
        }
    }

    public void setMovieDetails() throws Exception{
        String inputName = searchText.getText().replace(" ", "+");
        JSONObject movieDetails = MovieFetcher.fetchMovieDetails(inputName);

        if ("True".equals(movieDetails.getString("Response"))) {
            String title = movieDetails.getString("Title");
            String runtime = movieDetails.getString("Runtime");
            String imdbRating = movieDetails.getString("imdbRating");
            String posterUrl = movieDetails.getString("Poster");

            itemView.setName(title);
            itemView.setRunTime(runtime);
            itemView.setRating(imdbRating);
            itemView.setPosterUrl(posterUrl);

            movieNameText.setText(title);
            runTimeText.setText(runtime);
            ratingText.setText(imdbRating);
            poster.setImage(itemView.getPoster());
        } else {
            System.out.println("movie not found");
        }
    }

    public void setItemView(ItemView itemView) {
        this.itemView = itemView;
        this.movieNameText.setText(itemView.getName());
        this.runTimeText.setText(itemView.getRunTime());
        this.ratingText.setText(itemView.getRating());
        this.noteText.setText(itemView.getNotes());
        this.poster.setImage(itemView.getPoster());
    }

    public String getMovieName() {
        return this.movieNameText.getText();
    }

    public void setMovieName(String name) {
        this.movieNameText.setText(name);;
    }

    public String getRunTime() {
        return this.runTimeText.getText();
    }

    public void setRunTime(String time) {
        this.runTimeText.setText(time);
    }

    public String getNotes() {
        return this.noteText.getText();
    }

    public String getRating() {
        return this.ratingText.getText();
    }

    public void setRating(String rating) {
        this.ratingText.setText(rating);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
