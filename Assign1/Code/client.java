package Assign1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String argv[]) throws Exception {
        Socket echo;
        DataInputStream dis;
        DataOutputStream dos;
        Scanner scanner = new Scanner(System.in); //create scanner for user input
        echo = new Socket("localhost", 3500);
        dis = new DataInputStream(echo.getInputStream());
        dos = new DataOutputStream(echo.getOutputStream()); //create input/output streams on socket

        System.out.println("This is a multithreaded and multiclient application with a few different functions.");

        int choice = 0;

        while (choice != 4) {
            System.out.println("Enter 1 for the calculator, 2 for the local date, or 3 to put the server to sleep, 4 to exit.");
            choice = scanner.nextInt(); //grabbing choice input
            dos.writeInt(choice);
            dos.flush();
            if (choice == 1) {
                System.out.println("");
                System.out.println(
                        "After entering two numbers, this calculator will compute \n the sum, difference, product, and quotient.");
                int x = scanner.nextInt(); // Grabbing input
                int y = scanner.nextInt();

                // Writing data to server
                dos.writeInt(x);
                dos.flush();
                dos.writeInt(y);
                dos.flush();

                // Receiving data from server
                String str = (dis.readLine());
                System.out.println(str);
                str = (dis.readLine());
                System.out.println(str);
                str = (dis.readLine());
                System.out.println(str);
                str = (dis.readLine());
                System.out.println(str);
                System.out.println("End Calculations.");

            } else if (choice == 2) {
                String str = (dis.readLine());
                System.out.println(str);

            } else if (choice == 3) {
                System.out.println("Enter a number in seconds for how long you wish the server to sleep for.");
                int x = scanner.nextInt();
                dos.writeInt(x);
                dos.flush();
                String str = (dis.readLine());
                System.out.println(str);
                str = (dis.readLine());
                System.out.println(str);
            }
            System.out.println("");
        }
        scanner.close();
        echo.close();
    }
}
