import java.rmi.*;

public class FileServer {
   public static void main(String argv[]) {
      try {
         System.out.println("Server is ready!");
         FileInterface fi = new FileImpl();
         Naming.rebind("//127.0.0.1/FileServer", fi);
         System.out.println("Server is ready!2");
      } catch(Exception e) {
         System.out.println("FileServer: "+e.getMessage());
         e.printStackTrace();
      }
   }
}
