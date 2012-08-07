package replaychecker;

import java.io.*;
import java.net.*;
import java.util.*;

public class Repeater {
    
    private String url;
    private File file;
    private int interval=0;
    private int reqCount=5;
    
    public Repeater(File file,int interval,int count) {
        this.file=file;
        this.interval=interval;
        this.reqCount=count;
    }
    
    private void sendRequest() {
        try {
            InetAddress addr=InetAddress.getByName(this.getHost());
            Socket sock=new Socket(addr,80);
            PrintWriter stdout=new PrintWriter(sock.getOutputStream());
            Scanner fin=new Scanner(new FileInputStream(file));
            while(fin.hasNextLine()) stdout.println(fin.nextLine());
            stdout.println();
            stdout.flush();
            Scanner stdin=new Scanner(sock.getInputStream());
            while(true) {
                String line=stdin.nextLine();
                if(line.isEmpty() || line==null) break;
                System.out.println(line);
            }
            System.out.println("...response complete...");
            System.out.println();
        } catch(Exception e) {e.printStackTrace();}
    }
    
    private String getHost() {
        try {
            Scanner in=new Scanner(new FileInputStream(file));
            String host=null;
            while(in.hasNextLine()) {
                String line=in.nextLine();
                if(line.startsWith("Host:")) host=line.substring(5).trim();
            }
            return host;
        } catch(Exception e) {e.printStackTrace();return null;}
    }
    
    public void flood() {
        for(int i=0;i<reqCount;i++) {
            if(i>0) {
                try {
                    System.out.println("Waiting for "+this.interval+" seconds...");
                    Thread.sleep(this.interval*1000);
                } catch(Exception e) {e.printStackTrace();}
            }
            System.out.println("Sending request: "+i);
            System.out.println("....................");
            this.sendRequest();
        }
    }
}
