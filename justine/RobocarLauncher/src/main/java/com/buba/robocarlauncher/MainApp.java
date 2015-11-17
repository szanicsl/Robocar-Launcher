package com.buba.robocarlauncher;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author szanicsl
 */
public class MainApp extends Application {

    public String city = new String();
    public String osm = new String();
    public String txt = new String();
    public String team = new String();
    public String gngnum = new String();
    public String directory, validchecker;

    public void handleOptions(CheckBox box1, CheckBox box2) {
        if (box1.isSelected()) {
            city = "Debrecen";
            osm = "debrecen.osm";
            txt = "deb-lmap.txt";
        } else {
            city = "Teglas";
            osm = "teglas.osm";
            txt = "teglas-lmap.txt";
        }
    }

    private boolean isInt(TextField input, String message) {
        try {
            int num = Integer.parseInt(input.getText());
            System.out.println("Gangsters: " + num);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error: " + message + " is not a number");
            return false;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        /*DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Directory");
        chooser.showDialog(primaryStage);*/

        System.out.println(directory);

        final CheckBox box1 = new CheckBox("Debrecen");
        box1.setSelected(true);

        final CheckBox box2 = new CheckBox("Teglas");

        final CheckBox box3 = new CheckBox("Show Traffic");
        box3.setSelected(true);
        final CheckBox box4 = new CheckBox("Show Pedes");
        box4.setSelected(true);

        box1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                box2.setSelected(false);
            }
        });

        box2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                box1.setSelected(false);
            }
        });

        box3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Network connectiontowindow = new Network("localhost", 11100);
                if (box3.isSelected()) {
                    try {
                        connectiontowindow.connectToServer("showtraffic");
                        connectiontowindow.connected = false;
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                } else {
                    try {
                        connectiontowindow.connectToServer("donotshowtraffic");
                        connectiontowindow.connected = false;
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }

            }
        });
        box4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Network connectiontowindow = new Network("localhost", 11100);
                if (box4.isSelected()) {
                    try {
                        connectiontowindow.connectToServer("showpedestrians");
                        connectiontowindow.connected = false;
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                } else {
                    try {
                        connectiontowindow.connectToServer("donotshowpedestrians");
                        connectiontowindow.connected = false;
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }

            }
        });

        final TextField teamname = new TextField("Team Name");
        final TextField gngstrsnum = new TextField("Gangsters to Add");

        Button startr = new Button("Start Robocar");
        startr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Robocar Launcher");
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Robocar Emulator Directory");
                File defaultDirectory = new File("/home");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(secondaryStage);
                directory = selectedDirectory.toString();
                validchecker = directory + "/justine";
                File justine = new File(validchecker);
                if (justine.exists()) {
                    try {
                        handleOptions(box1, box2);
                        secondaryStage.close();
                        Process first = Runtime.getRuntime().exec(new String[]{"xterm", "-e", "sh", "-c", directory + "/justine/rcemu/src/smartcity --osm=" + directory + "/justine/" + osm + " --city=" + city + " --shm=SharedMemory --node2gps=" + directory + "/justine/" + txt});
                        Thread.sleep(8000);
                        Process second = Runtime.getRuntime().exec(new String[]{"xterm", "-e", "sh", "-c", directory + "/justine/rcemu/src/traffic --port=10007 --shm=SharedMemory"});
                        Thread.sleep(3000);
                        Process third = Runtime.getRuntime().exec(new String[]{"xterm", "-e", "bash", "-c", "java -jar " + directory + "/justine/rcwin/target/site/justine-rcwin-0.0.16-jar-with-dependencies.jar " + directory + "/justine/" + txt});

                        System.out.println("Robocar Started!");
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                    } catch (InterruptedException ex) {
                        System.out.println(ex.toString());
                    }
                } else {
                    System.out.println("This path is incorrect!");
                }
            }
        });

        Button addg = new Button("Add Gangster");
        addg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isInt(gngstrsnum, gngstrsnum.getText())) {
                    Network connection = new Network("localhost", 10007);
                    gngnum = gngstrsnum.getText();
                    try {
                        connection.connectToServer("<init 0 " + gngnum + " g>");
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    System.out.println("Gangster Added!");
                } else {
                    System.out.println("You can't add gangsters!");
                }
            }
        });

        Button addteam = new Button("Add Team");
        addteam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                team = teamname.getText();
                try {
                    Process first = Runtime.getRuntime().exec(new String[]{"xterm", "-e", "sh", "-c", "/home/szanicsl/prog2harmadikvedes/robocar/robocar-emulator/justine/rcemu/src/samplemyshmclient --port=10007 --shm=SharedMemory --team=" + team});
                    System.out.println("Team Added!");
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        vbox.getChildren().addAll(startr, addteam, addg, box1, box2, teamname, gngstrsnum, box3, box4);

        BorderPane border = new BorderPane();
        border.setCenter(vbox);
        primaryStage.setTitle("Robocar Launcher");
        primaryStage.setScene(new Scene(border, 300, 300));

        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }

}
