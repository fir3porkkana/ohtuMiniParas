package ohtu.app;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileSelector {

  private Stage stage;
  FileChooser fileChooser = new FileChooser();
  ExtensionFilter audioFilter = new ExtensionFilter("Audio Files", "*.mp3");
  ExtensionFilter pictureFilter = new ExtensionFilter("Picture files", "*.jpg", "*.png");

  public FileSelector(Stage stage) {
    this.stage = stage;
  }

  public File openAudioBrowser() {

    fileChooser.setTitle("Choose audio file");
    fileChooser.getExtensionFilters().add(audioFilter);

    File selectedFile = fileChooser.showOpenDialog(stage);
    fileChooser.getExtensionFilters().remove(audioFilter);

    return selectedFile;

  }

  public File openImageBrowser() {
    fileChooser.getExtensionFilters().add(pictureFilter);
    fileChooser.setTitle("Choose picture file");

    File selectedFile = fileChooser.showOpenDialog(stage);
    fileChooser.getExtensionFilters().remove(pictureFilter);

    return selectedFile;
  }
}
