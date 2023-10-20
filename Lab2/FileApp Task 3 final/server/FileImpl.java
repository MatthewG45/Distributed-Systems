import java.io.*;
import java.rmi.*;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class FileImpl extends UnicastRemoteObject
  implements FileInterface {
   private String s1;

   public FileImpl(String s) throws RemoteException{
      super();
   }
   

   public byte[] downloadFile(String fileName){
      try {
         //Downloading file
         String s = getClientHost();
         System.out.println("The Client host is: "+s);
         System.out.println("Downloading the file...");
         File file = new File(fileName);
         System.out.println("The Name of File requested is: "+fileName);
         byte buffer[] = new byte[(int)file.length()];
         BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
         input.read(buffer,0,buffer.length);
         input.close();
         System.out.println("Download complete.");
         return(buffer);
      } catch(Exception e){
         System.out.println("FileImpl: "+e.getMessage());
         e.printStackTrace();
         return(null);
      }
   }
}