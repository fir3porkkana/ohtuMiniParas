package ohtu.app.components;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileSelector {

  private Stage stage;
  FileChooser fileChooser = new FileChooser();

  public FileSelector(Stage stage) {
    this.stage = stage;
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
        new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"), new ExtensionFilter("All Files", "*.*"));

  }

  public File openFileBrowser() {

    File selectedFile = fileChooser.showOpenDialog(stage);
    return selectedFile;

  }
}