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

  }

  public void setTitle(String title) {
    fileChooser.setTitle(title);

  }

  public void addFilter(ExtensionFilter filter) {
    fileChooser.getExtensionFilters().addAll(filter);
  }

  public File openFileBrowser() {

    File selectedFile = fileChooser.showOpenDialog(stage);
    return selectedFile;

  }
}