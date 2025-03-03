package org.example.movieappexample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HelloController {
 @FXML ImageView moviePosterImageView;
 @FXML ChoiceBox<String> movieChoiceBox;
 @FXML Label titleLabel;
    @FXML Label yearLabel;
    @FXML Label ratingLabel;
    @FXML Label directorLabel;
    @FXML Label actorsLabel;
    @FXML Label plotLabel;

    public void initialize() {
        movieChoiceBox.getItems().addAll("Jurassic Park", "Jurassic World", "Skyfall", "Casino Royale", "Spectre", "Star Trek", "The Lord of the Rings", "Indiana Jones", "Star Wars", "Back to the Future", "Iron Man", "Captain America", "Thor"
        );//listener has to be change listener
        movieChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String title) {
                String apiCallString = "http://www.omdbapi.com/?t=" + title.replace(" ", "%20") + "&apikey=c8297d74";
                String jsonString;
                String movieTitle;
                String movieYear;
                String movieRating;
                String movieDirector;
                String movieActors;
                String moviePlot;
                URL apiURL = null;
                StringBuffer content = new StringBuffer();
                //Create API connection and get JSON string of data
                try{
                    apiURL = new URL(apiCallString);
                    HttpURLConnection httpConnection = (HttpURLConnection)apiURL.openConnection();
                    httpConnection.setRequestMethod("GET");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    while ((jsonString = bufferedReader.readLine()) != null) {
                        content.append(jsonString);
                    }
                    bufferedReader.close();
                    httpConnection.disconnect();

                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
                //assigning json objects to in program objects
                
                JSONObject movieObject = new JSONObject(content.toString());
                movieTitle = movieObject.getString("Title");
                movieYear = movieObject.getString("Actors");
                movieDirector = movieObject.getString("Director");
                movieActors = movieObject.getString("Actors");
                moviePlot = movieObject.getString("Plot");
                movieRating = movieObject.getJSONArray("Ratings").getJSONObject(0).getString("Value");
                String moviePosterURL = movieObject.getString("Poster");
                titleLabel.setText(movieTitle);
                yearLabel.setText(movieYear);
                directorLabel.setText(movieDirector);
                actorsLabel.setText(movieActors);
                plotLabel.setText(moviePlot);
                ratingLabel.setText(movieRating);
                
                Image moviePoster = new Image(moviePosterURL);
                moviePosterImageView.setImage(moviePoster);
                
            }
        });
    }

}