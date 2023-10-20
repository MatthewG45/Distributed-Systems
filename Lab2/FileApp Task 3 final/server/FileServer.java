import java.rmi.*;

public class FileServer {
   public static void main(String argv[]) {
      try {
         System.out.println("Getting server...");
         FileInterface fi = new FileImpl("FileServer");
         Naming.rebind("//127.0.0.1/FileServer", fi);
         System.out.println("Server is ready!");
      } catch(Exception e) {
         System.out.println("FileServer: "+e.getMessage());
         e.printStackTrace();
      }
   }
}
