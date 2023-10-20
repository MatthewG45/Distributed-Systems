import java.io.*; 
import java.rmi.*;

public class FileClient{
   public static void main(String argv[]) {
      if(argv.length != 3) {
        System.out.println("Usage: java FileClient fileName machineName choice(1-download, 2-upload)");
        System.exit(0);
      }
      if (argv[2] == "1") {
         try {
            System.out.println("Downloading the file...");
            String name = "//" + argv[1] + "/FileServer";
            FileInterface fi = (FileInterface) Naming.lookup(name);
            byte[] filedata = fi.downloadFile(argv[0]);
            File file = new File(argv[0]);
            BufferedOutputStream output = new
            BufferedOutputStream(new FileOutputStream(file.getName()));
            output.write(filedata,0,filedata.length);
            output.flush();
            output.close();
            System.out.println("Finished downloading!");
         } catch(Exception e) {
            System.err.println("FileServer exception: "+ e.getMessage());
            e.printStackTrace();
         }
      } else if (argv[2] =="2") {
         try {
            System.out.println("Uploading the file...");
            String name = "//" + argv[1] + "/FileServer";
            FileInterface fi = (FileInterface) Naming.lookup(name);
            byte[] filedata = fi.uploadFile(argv[0]);
            File file = new File(argv[0]);
            BufferedOutputStream output = new
            BufferedOutputStream(new FileOutputStream(file.getName()));
            output.write(filedata,0,filedata.length);
            output.flush();
            output.close();
            System.out.println("Finished downloading!");
         } catch(Exception e) {
            System.err.println("FileServer exception: "+ e.getMessage());
            e.printStackTrace();
         }
      }
   }
}