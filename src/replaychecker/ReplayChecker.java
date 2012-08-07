package replaychecker;

public class ReplayChecker {
    
    public static void main(String[] args) {
        
        if(args.length!=5) {
            System.out.println("Invalid number of arguements.");
            System.out.println();
            System.out.println("General Usage: java -jar <toolname> [args]");
            System.out.println("Arguement Sequence: <filename> <number of replays> <delay> <gmail id> <gmail password>");
            System.out.println("Note: File must contain request data.");
            System.exit(1);
        }
        
        InboxCheck ibc=new InboxCheck();
        Repeater r=new Repeater(new java.io.File(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
        Checker c=new Checker(ibc,r);
        c.exec(args[3], args[4]);
    }
}
