package replaychecker;

import javax.mail.*;
import javax.mail.search.*;
import java.util.*;

public class InboxCheck {
    
    private Properties props;
    
    public InboxCheck() {
        props=System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
    }
    
    private Store connect(String username,String password) {
        try {
            Session sess=Session.getDefaultInstance(props);
            Store store=sess.getStore("imaps");
            store.connect("imap.gmail.com",username+"@gmail.com",password);
            return store;
        } catch(Exception e) {e.printStackTrace();return null;}
    }
    
    private Folder getInbox(Store store) {
        try {
            Folder inbox=store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            return inbox;
        } catch(Exception e) {e.printStackTrace();return null;}
    }
    
    private Message[] getCurrentMail(String user,String pass) {
        Store s=connect(user,pass);
        Folder inb=this.getInbox(s);
        try {
            Message msgs[]=inb.search(new ReceivedDateTerm(DateTerm.EQ,new Date(new Date().getTime())));
            return msgs;
        } catch(Exception e) {e.printStackTrace();return null;}
        
    }
    
    public void getMailData(String user,String pass) {
        Message m[]=this.getCurrentMail(user, pass);
        for(int i=0;i<m.length;i++) {
            try {
                Address a[]=m[i].getFrom();
                for(int j=0;j<a.length;j++) System.out.println(a[j].toString());
                System.out.println("SUBJECT: "+m[i].getSubject());
                Multipart mp=(Multipart)m[i].getContent();
                for(int k=0;k<mp.getCount()/2;k++) System.out.println(mp.getBodyPart(i).getContent());
            } catch(Exception e) {e.printStackTrace();}
        }
    }
    
    public int getCurrentCount(String user,String pass) {
        Store s=this.connect(user, pass);
        Folder ib=this.getInbox(s);
        try {
            int count=ib.getUnreadMessageCount();
            return count;
        } catch(Exception e) {e.printStackTrace();return -1;}
    }
}
