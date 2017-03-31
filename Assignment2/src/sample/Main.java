package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import javafx.scene.Cursor;

import static java.lang.System.out;


public class Main extends Application {
    private BorderPane layout;
    ListView <String> sharedFolderLocal = new ListView<>();
    ListView <String> sharedFolderServer = new ListView<>();

    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 500, 580));

        layout = new BorderPane();
        Scene scene = new Scene(layout, 500, 580);


        /* Create an Edit Form (For the Top of the User Interface */
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(0, 0, 0, 0));
        editArea.setVgap(0);
        editArea.setHgap(0);

        /* Download */



        Button download = new Button("Download");
        editArea.add(download, 0, 0);
        download.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ObservableList<String> fileNamesS = FXCollections.observableArrayList();
                String selectedItem = sharedFolderServer.getSelectionModel().getSelectedItem();
                fileNamesS.add(selectedItem);
                sharedFolderLocal.getItems().addAll(fileNamesS);

                try {
                    Socket sock = new Socket("127.0.0.1", 8080);
                    //out.println("Download");
                    byte[] mybytearray = new byte[1024];
                    InputStream is = sock.getInputStream();
                    String name = selectedItem;
                    out.println(name);
                    FileOutputStream fos = new FileOutputStream("//home//dikachi//Desktop//Assignment2//client//" + name);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    bos.write(mybytearray, 0, bytesRead);
                    bos.close();
                    sock.close();

                }catch(Exception e){
                    out.println("DIDNT WORK");
                }

            }
        });

        /* Upload */
        Button upload = new Button("Upload");
        editArea.add(upload, 1, 0);
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                out.println("upload");
                ObservableList<String> fileNamesS = FXCollections.observableArrayList();
                String selectedItem = sharedFolderLocal.getSelectionModel().getSelectedItem();
                fileNamesS.add(selectedItem);
                sharedFolderServer.getItems().addAll(fileNamesS);

                try {
                    ServerSocket servsock = new ServerSocket(8080);
                    File myFile = new File(selectedItem);
                    while (true) {
                        Socket sock = servsock.accept();
                        byte[] mybytearray = new byte[(int) myFile.length()];
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
                        bis.read(mybytearray, 0, mybytearray.length);
                        OutputStream os = sock.getOutputStream();
                        os.write(mybytearray, 0, mybytearray.length);
                        os.flush();
                        sock.close();
                    }
                } catch (Exception e) {
                    out.println("DIDNT WORK");
                }
            }
            });

        /* SplitPane */
        SplitPane splitPane = new SplitPane();
        splitPane.setPrefWidth(scene.getWidth());
        splitPane.setPrefHeight(scene.getHeight());


        listFileslocal("//home//dikachi//Desktop//Assignment2//client");
        listFilesserver("//home//dikachi//Desktop//p//server");

        splitPane.getItems().addAll(sharedFolderLocal, sharedFolderServer);
        splitPane.setDividerPosition(2, 0.5);

        /* Arranging All Components in the User Interface */
        layout.setTop(editArea);
        layout.setBottom(splitPane);

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public void listFileslocal(String directoryName){
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                sharedFolderLocal.getItems().add(file.getName());
            }
        }

    }

    public void listFilesserver(String directoryName){
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                sharedFolderServer.getItems().add(file.getName());
            }
        }
    }

    public void download(String n)throws IOException
    {
        Socket sock = new Socket("127.0.0.1", 8080);
        byte[] mybytearray = new byte[1024];
        InputStream is = sock.getInputStream();
        String name = "//home//dikachi//Desktop//sharedlocal//" + n;
        FileOutputStream fos = new FileOutputStream(name);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = is.read(mybytearray, 0, mybytearray.length);
        bos.write(mybytearray, 0, bytesRead);
        bos.close();
        sock.close();
    }


    public static void main(String[] args) {

        launch(args);
    }
}

