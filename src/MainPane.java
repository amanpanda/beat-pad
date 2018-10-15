import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class for the app. Initiates a window that contains a melodymaker, beatpad, and sidebar. Run this file.
 * Authors: Aman, Micah, Noah, Abha
 * Date: 3/5/19
*/
public class MainPane extends Application{

    //Instance variables
    private Controller controller;
    private BeatPadPane beatPadPane;
    private SideBarPane sideBar;
    private MelodyMakerPane melodyMaker;
    private boolean isOpen;

    /**
     * Override the start method of the application class to set scene
     * for the app. Create instances of beatpadpane, sidebarpane, and
     * melodymakerpane and place them appropriately in a borderpane.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        isOpen = false;
        ModelInterface m = new Model();
        this.controller = new Controller(m, this);
        // Borderpane root will contain all other panes

        BorderPane root = new BorderPane();

        // Create instances of the various sub-panes in our app
        this.beatPadPane = new BeatPadPane(controller);
        this.melodyMaker = new MelodyMakerPane(controller);
        FlowPane centerPane = new FlowPane();

        centerPane.getChildren().addAll(beatPadPane.getRoot(), melodyMaker.getRoot());
        this.sideBar = new SideBarPane(controller);

        // Apply appropriate styles
        root.getStyleClass().add("root");
        beatPadPane.getRoot().getStyleClass().add("beatPad");
        melodyMaker.getRoot().getStyleClass().add("melodyMaker");
        sideBar.getRoot().getStyleClass().add("sidebar");
        centerPane.getStyleClass().add("centerPane");

        // Place subpanes in their appropriate place in the root
        root.setRight(sideBar.getRoot());
        root.setCenter(centerPane);

        // Initialize map, to be used for keyboard shortcuts to push buttons on beatpad
        Map<String, ButtonBase> buttonMap = getShortcutMap(beatPadPane.getSampleButtons(), beatPadPane.getLoopButtons());

        // Listener to trigger button presses based on keyboard input
        root.setOnKeyPressed(event -> {
            ButtonBase b = buttonMap.get(event.getText());
            if(b != null) {
                b.fire();
            }
        });

        // Create application scene
        Scene scene = new Scene(root, 1020, 730);
        scene.getStylesheets().add("MainPaneStyling.css");
        primaryStage.setTitle("Beatpad");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> isOpen = false);

        isOpen = true;
        Thread mainLoop = new Thread() {
            public void run(){
                try {
                    controller.mainLoop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mainLoop.start();

    }

    /*
     * Function that returns a hash-map of keyboard buttons (in their string representations")
     * mapping to associated buttons in a beatpad and melodymaker, respectively.
     * Takes in as input Button[][] samplebuttons and ToggleButton[][] loopbuttons.
     */
    private Map<String, ButtonBase> getShortcutMap(AudioButton[][] sampleButtons, AudioButton[][] loopButtons){

        // Initialize a hashmap
        Map<String, ButtonBase> toReturn = new HashMap<>();

        // Keyboard keys to map
        String[][] sampleKeys = {{"q", "w"},
                                    {"a", "s"},
                                    {"z", "x"}};
        String[][] loopKeys = {{"7","8","9"},
                                  {"4","5","6"},
                                  {"1","2","3"}};

        // Loop through sample buttons and map keyboard keys to samplebuttons accordingly.
        for(int i = 0; i < sampleButtons.length; i++){
            for(int j = 0; j < sampleButtons[i].length; j++){
                toReturn.put(sampleKeys[i][j], sampleButtons[i][j].getBtn());
            }
        }

        // Loop through loop buttons and map keyboard keys to loopbuttons accordingly.
        for(int i = 0; i < loopButtons.length; i++){
            for(int j = 0; j < loopButtons[i].length; j++){
                toReturn.put(loopKeys[i][j], loopButtons[i][j].getBtn());
            }
        }

        // Return hashmap once mapping has finished
        return toReturn;
    }

    /**
     * Gets the BeatPadPane held by this class
     * @return the BeatPadPane
     */
    public BeatPadPane getBeatPadPane() {
        return beatPadPane;
    }

    /**
     * Gets the SideBarPane held by this class
     * @return the SideBarPane
     */
    public SideBarPane getSideBarPane() {
        return sideBar;
    }

    /**
     * Gets the MelodyMakerPane held by this class
     * @return MelodyMakerPane
     */
    public MelodyMakerPane getMelodyMakerPane() { return melodyMaker; }

    /**
     * Is the window currently open?
     * @return true if the window is open, false otherwise.
     */
    public boolean isOpen() { return isOpen; }

    /*
     * Main method for app. Run this.
     */
    public static void main(String[] args) throws InterruptedException{
        launch(args);
    }
}
