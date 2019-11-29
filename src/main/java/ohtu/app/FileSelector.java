package ohtu.app;

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
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Audio Files", "*.mp3"));

  }

  public File openFileBrowser() {

    File selectedFile = fileChooser.showOpenDialog(stage);
    return selectedFile;

  }
}