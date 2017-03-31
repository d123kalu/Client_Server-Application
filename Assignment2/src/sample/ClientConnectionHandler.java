package sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionHandler {
    public static String DIR = "Server";
    private Socket socket = null;
    private BufferedReader bufferedReader = null;
    private DataOutputStream dataOutputStream = null;
    public ClientConnectionHandler(Socket socket) {
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server Error While Processing New Socket\r\n");
            e.printStackTrace();
        }
    }
}