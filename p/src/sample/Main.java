package sample;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        ServerSocket m_ServerSocket = new ServerSocket(8080);
        int id = 0;
        while (true) {
            Socket clientSocket = m_ServerSocket.accept();
            ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
            cliThread.start();
        }
    }
}

class ClientServiceThread extends Thread {
    Socket clientSocket;
    int clientID = -1;
    boolean running = true;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
    }

    public void run() {
        System.out.println("Accepted Client : ID - " + clientID + " : Address - "
                + clientSocket.getInetAddress().getHostName());
        try {
            BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            while (running) {
                String clientCommand = in.readLine();
                System.out.println("Client Says :" + clientCommand);
                if (clientCommand.equalsIgnoreCase("download")) {
                    String filename = in.readLine();
                    File myFile = new File("//home//dikachi//Desktop//p//server//" + filename);
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
                    bis.read(mybytearray, 0, mybytearray.length);
                    OutputStream os = clientSocket.getOutputStream();
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                }
                if (clientCommand.equalsIgnoreCase("upload")) {
                    byte[] mybytearray = new byte[1024];
                    InputStream is = clientSocket.getInputStream();
                    String name = in.readLine();
                    FileOutputStream fos = new FileOutputStream("//home//dikachi//Desktop//Assignment2//client//" + name);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    bos.write(mybytearray, 0, bytesRead);
                    bos.close();
                    clientSocket.close();

                }
                else {
                    System.out.println("didnt work");
                    out.flush();
                    clientSocket.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
