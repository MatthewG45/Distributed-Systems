package Assign1;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.*;

public class server extends Thread{
    private ServerSocket server; //creating server socket
    
    public static void main(String argv[]) throws Exception {
        new server();
    }

    public server() throws Exception {
        server = new ServerSocket(3500);
        System.out.println("Server Listening on Port 3500....");
        this.start(); //starting the run() method
    }

    @SuppressWarnings("unused")
    public void run() {
        while (true) {
            try { //Here I am just accepting the connection
                System.out.println("Waiting for connections.");
                Socket client = server.accept();
                System.out.println("Accepted new connection from: "+client.getRemoteSocketAddress());
                Thread t = new MainThread(client); // Here, I am starting a thread with the MainThread class
                t.start(); //Starting the Thread
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


class MainThread extends Thread {
    private Socket client = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;

    public MainThread(Socket s) {
        this.client = s; //contructing
    }

    public void run() {
        while (true) { //always running server, to accept multiple clients at once
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());

                int choice = dis.readInt(); //first read is for the user choice

                if (choice == 1) { //if user selected 1, then start four separate subthreads, each for a different result.
                    int x = dis.readInt();
                    System.out.println("I got: " + x);
                    int y = dis.readInt();
                    System.out.println("I got: " + y); //grabbing and displaying user input from clients (for diagnostics, usually the server isnt visible)

                    Thread thread_adder = new adder(dis, dos, x, y); //creating new thread on the adder class with the inputs
                    thread_adder.start();
                    Thread thread_difference = new difference(dis, dos, x, y);
                    thread_difference.start();
                    Thread thread_product = new product(dis, dos, x, y);
                    thread_product.start();
                    Thread thread_quotient = new quotient(dis, dos, x, y);
                    thread_quotient.start();
                    
                } else if (choice == 2) { //user chooses the date time, so instead start a single subthread on the currentdate class
                    Thread thread_date = new currentdate(dis, dos);
                    thread_date.start();
                } else if (choice == 3) { //This took me a WHILE to figure out hahaha. 
                    client.close(); // Closing the client socket if they choose to exit
                    return; // returning here is KEY, if not, the server will continue to try and write to the outputstream
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

class adder extends Thread { //Its a big class, but its very simple, it just adds the inputs together
    private DataInputStream dis;
    private DataOutputStream dos;
    private int a;
    private int b; // declaring local variables
    private double ans;

    public static double add(int a, int b) { //simply adding inputs
        return a+b; 
    }

    public adder(DataInputStream dis, DataOutputStream dos, int a, int b) {
        this.dis = dis;
        this.dos = dos;
        this.a = a;
        this.b = b; //constructing local variables
    }

    public void run() {
        try {
            Thread.sleep(100); //sleeping for a bit to make sure it sends in a determined order relative to the other threads.
            ans = add(a,b);
            System.out.println("Sending the addition answer...");
            dos.writeBytes("The sum is: "+ans+"\n");//write to client
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

//The next three classes are very similar to the adder class, see above for comments on them.
//Scroll down to the currentdate class for more information
class difference extends Thread {
    private DataInputStream dis;
    private DataOutputStream dos;
    private int a;
    private int b;
    private double ans;

    public static double subtract(int a, int b) {
        return a-b;
    }

    public difference(DataInputStream dis, DataOutputStream dos, int a, int b) {
        this.dis = dis;
        this.dos = dos;
        this.a = a;
        this.b = b;
    }

    public void run() {
        try {
            Thread.sleep(200);
            ans = subtract(a,b);
            System.out.println("Sending the difference answer...");
            dos.writeBytes("The difference is: "+ans+"\n");
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

class product extends Thread {
    private DataInputStream dis;
    private DataOutputStream dos;
    private int a;
    private int b;
    private double ans;

    public static double multiply(int a, int b) {
        return a*b;
    }

    public product(DataInputStream dis, DataOutputStream dos, int a, int b) {
        this.dis = dis;
        this.dos = dos;
        this.a = a;
        this.b = b;
    }

    public void run() {
        try {
            Thread.sleep(300);
            ans = multiply(a,b);
            System.out.println("Sending the multiplication answer...");
            dos.writeBytes("The product is: "+ans+"\n");
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

class quotient extends Thread {
    private DataInputStream dis;
    private DataOutputStream dos;
    private int a;
    private int b;
    private double ans;

    public static double divide(int a, int b) {
        if (b == 0) {
            return -1;
        }
        
        return a/b;
    }

    public quotient(DataInputStream dis, DataOutputStream dos, int a, int b) {
        this.dis = dis;
        this.dos = dos;
        this.a = a;
        this.b = b;
    }

    public void run() {
        try {
            Thread.sleep(400);
            ans = divide(a,b);
            System.out.println("Sending the addition answer...");
            dos.writeBytes("The quotient is: "+ans+"\n");
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

class currentdate extends Thread { //this class simply returns the localdate of the server to the client.
    private DataInputStream dis;
    private DataOutputStream dos;

    public static String getDate() {
        return LocalDate.now().toString(); //convert the date to a string.
    }

    public currentdate(DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
    }

    public void run() {
        try {
            Thread.sleep(100); //small sleep here just to keep things working smoothly
            String date = getDate();
            System.out.println("Sending the date...");
            dos.flush(); //flushing the output stream just to make sure its empty
            dos.writeBytes("The date is: " + date + "\n");
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
    }
}