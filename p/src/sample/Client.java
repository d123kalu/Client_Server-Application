package sample;

/**
 * Created by dikachi on 29/03/17.
 */
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String [] args) throws Exception {
        int number, temp;
        Scanner sc = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1", 1342);
        Scanner sc1 = new Scanner(s.getInputStream());
        System.out.println("Enter any number");
        number = sc.nextInt();
        PrintStream p = new PrintStream(s.getOutputStream());
        p.println(number);
        temp = sc1.nextInt();
        System.out.println(temp);
    }
}

