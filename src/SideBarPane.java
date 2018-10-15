import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

/**
 * Class that holds the side pane with all the information and volume controls etc.
 * **/

public class SideBarPane {

    private Node root;
    private Controller controller;
    private Label one;
    private Label two;
    private Label three;
    private Label four;
    private BeatPadPane beatPad;

    /**
     * Initialize an instance of the SideBarPane
     */
    public SideBarPane(Controller controller) {
        root = buildSideBarPane();
        this.controller = controller;
        beatPad=new BeatPadPane(controller);
    }

    /**
     * Allows us to access instances of SideBarPane in other files
     * @return the root node of the SideBarPane
     */
    //
    public Node getRoot() {
        return root;
    }

    /**
     * Update the visual counter based on the internal time
     * @param beat the current beat
     */
    public void updateCounter(int beat) {
        if(beat == 0) {one.getStyleClass().add("counter-on"); four.getStyleClass().remove("counter-on");}
        if(beat == 4) {two.getStyleClass().add("counter-on"); one.getStyleClass().remove("counter-on");}
        if(beat == 8) {three.getStyleClass().add("counter-on"); two.getStyleClass().remove("counter-on");}
        if(beat == 12) {four.getStyleClass().add("counter-on"); three.getStyleClass().remove("counter-on");}
    }

    /*
     * This function adds our necessary components to an instance of the sidebar
     */
    private VBox buildSideBarPane() {
        VBox sidebar = new VBox();
        sidebar.setPadding(new Insets(10));
        sidebar.setSpacing(10);

        /* Project title */
        Label title = new Label("It's Lit");
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
        title.setPadding(new Insets(20, 0, 0, 110));
        sidebar.getChildren().add(title);

        /* Project description text */
        Text description = new Text("Make music with the virtual beatpad and melodymaker!  " +
                                    "Click a loop to get a beat going, tap samples to instantly add " +
                                    "flavor, and choose melody notes to add your own spin. Made by Noah " +
                                    "Brackenbury, Abha Laddha, Micah Nacht, and Aman Panda");
        description.setFont(Font.font("Helvetica", 18));
        description.setWrappingWidth(310);
        sidebar.getChildren().add(description);

        /* Volume control slider */
        Slider volume = new Slider(0, 100, 80);
        volume.setPadding(new Insets(50, 0, 0, 0));
        volume.setShowTickLabels(true);
        volume.setBlockIncrement(10);
        volume.setMajorTickUnit(10);
        volume.setMinorTickCount(0);

        /* Listener added to volume in order to change it  */
        sidebar.getChildren().add(volume);
        volume.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                controller.updateVolume(volume.getValue());
            }
        });

        /* Volume Caption */
        Label volumeCaption = new Label("volume");
        volumeCaption.getStyleClass().add("sidebar-label");
        volumeCaption.setPadding(new Insets(0, 0, 0, 132));
        sidebar.getChildren().add(volumeCaption);

        /*
         * This counter displays the internal time of the beatpad so as to make the product
         * more accessible to users unfamiliar with drum machines or music theory
         */
        HBox counter = new HBox();
        one = new Label("1");
        two = new Label("2");
        three = new Label("3");
        four = new Label("4");
        one.getStyleClass().add("counter");
        two.getStyleClass().add("counter");
        three.getStyleClass().add("counter");
        four.getStyleClass().add("counter");
        one.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        two.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        three.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        four.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));

        /*
         * Each number of the counter is a separate label so that I can make them
         * light up at the appropriate times
         */
        counter.getChildren().addAll(one, two, three, four);
        counter.setSpacing(20);
        counter.setAlignment(Pos.CENTER);
        counter.setPadding(new Insets(50, 0, 0, 0));
        sidebar.getChildren().add(counter);

        /* Label Caption */
        Label counterCaption = new Label("counter");
        counterCaption.getStyleClass().add("sidebar-label");
        counterCaption.setPadding(new Insets(0, 0, 0, 132));
        sidebar.getChildren().add(counterCaption);

        /*
         * Preset buttons.  The "Clear" button will clear all selected loops and melody notes
         * Preset 1 will select kick (1), hi-hat (2), and snare (4) loops
         * Preset 2 will select kick (1), hi-hat lick (3), and toms (5) loops
         * Preset 3 will select 808 kick (7), ting (8), and clap (9) loops
         */

        HBox presets = new HBox();
        Button presetOne = new Button("1");
        Button presetTwo = new Button("2");
        Button presetThree = new Button("3");
        presetOne.setPrefSize(45,45);
        presetTwo.setPrefSize(45,45);
        presetThree.setPrefSize(45,45);

        presets.getChildren().addAll(presetOne, presetTwo, presetThree);
        presets.setSpacing(10);
        presets.setAlignment(Pos.CENTER);
        presets.setPadding(new Insets(50, 0, 0, 0));
        sidebar.getChildren().add(presets);

        presetOne.setOnMouseClicked(event ->
        {
            controller.turnOnLoop(0,0);
            controller.turnOnLoop(0,1);
            controller.turnOnLoop(1,0);
        });

        presetTwo.setOnMouseClicked(event ->
        {
            controller.turnOnLoop(0,0);
            controller.turnOnLoop(0,2);
            controller.turnOnLoop(1,1);
        });

        presetThree.setOnMouseClicked(event ->
        {
            controller.turnOnLoop(2,0);
            controller.turnOnLoop(2,1);
            controller.turnOnLoop(2,2);
        });

        /* Preset Label */
        Label presetsCaption = new Label("presets");
        presetsCaption.getStyleClass().add("sidebar-label");
        presetsCaption.setPadding(new Insets(0, 0, 0, 132));
        sidebar.getChildren().add(presetsCaption);

        /* Clear button that will deselect all selected loops and melody notes */
        HBox clearSpace = new HBox();
        Button clear = new Button("clear all");
        clear.setOnMouseClicked(event -> {
            controller.removeAllSoundsFromModel();
            controller.clearLoopAndMelodyButtons();
        });
        clearSpace.setAlignment(Pos.CENTER);
        clear.setPrefSize(100,50);
        clearSpace.setPadding(new Insets(50,0,0,0));
        clearSpace.getChildren().add(clear);
        sidebar.getChildren().add(clearSpace);

        return sidebar;
    }
}
