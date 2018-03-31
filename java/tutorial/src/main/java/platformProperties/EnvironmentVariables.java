package platformProperties;

import java.util.Map;

public class EnvironmentVariables {
//    java -DsysProp1=asf EnvironmentVariables arg1 arg2
    public static void main(String[] args) {

//        Env variables
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }

//        -DsysProp1=asf
        System.out.println(System.getProperties());
        System.out.println(System.getProperty("user.home"));

//        arg1 arg2
        for (int i = 0; i < args.length; i++) {
            System.out.println("arg#" + i + "= " + args[i]);
        }
    }
}
