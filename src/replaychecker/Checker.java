package replaychecker;

public class Checker {
    
     private InboxCheck ibc;
     private Repeater rep;
     
     public Checker(InboxCheck ibc,Repeater rep) {
         this.ibc=ibc;
         this.rep=rep;
     }
     
     public void exec(String user,String pass) {
         int oldcnt=ibc.getCurrentCount(user, pass);
         rep.flood();
         try {
             System.out.println("Waiting 5 secs for mail(s) to reach inbox");
             Thread.sleep(5000);
         } catch(Exception e) {e.printStackTrace();}
         int newcnt=ibc.getCurrentCount(user, pass);
         if(newcnt>oldcnt) {
             System.out.println("Replay Attack Detected.");
             System.out.println(".......................");
         }
         else System.out.println("No Replay Attack Detected.");
     }
}
