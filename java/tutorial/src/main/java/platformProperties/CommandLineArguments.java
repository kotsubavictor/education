package platformProperties;

public class CommandLineArguments {
//    java CommandLineArguments arg1 arg2 etc...
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("arg#" + i + "= " + args[i]);
        }
    }
}
