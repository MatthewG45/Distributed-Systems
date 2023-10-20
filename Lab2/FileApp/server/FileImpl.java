import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileImpl implements FileInterface, Serializable {

   public static void main(String[] args) {
      try {
      FileInterface fileInterface = new FileImpl();
      FileInterface stub = (FileInterface) UnicastRemoteObject.exportObject(fileInterface, 1099);
      Registry registry = LocateRegistry.getRegistry();
      registry.bind("fileInterface", stub);
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println(e.getMessage());
      }
   }

   public byte[] downloadFile(String fileName){
      try {
         
         File file = new File(fileName);
         byte buffer[] = new byte[(int)file.length()];
         BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
         input.read(buffer,0,buffer.length);
         input.close();
         return(buffer);
      } catch(Exception e){
         System.out.println("FileImpl: "+e.getMessage());
         e.printStackTrace();
         return(null);
      }
   }
}