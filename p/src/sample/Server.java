/**
 * Created by dikachi on 29/03/17.
 */
package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Server {
    public static void main(String [] args) throws UnknownHostException,IOException {
         int number,temp;
         ServerSocket s1=new ServerSocket(1342);
         Socket ss=s1.accept();
         Scanner sc = new Scanner(ss.getInputStream());
         number=sc.nextInt();

         temp=number*2;

         PrintStream p = new PrintStream(ss.getOutputStream());
         p.println(temp);


    }
}