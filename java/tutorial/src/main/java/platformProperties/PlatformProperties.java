package platformProperties;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PlatformProperties {
    public static void main(String[] args) throws Exception {
        // create and load default properties
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("./tutorial/src/main/resources/platformProperties/default.properties");
        defaultProps.load(in);
        in.close();

        // create application properties with default values
        Properties applicationProps = new Properties(defaultProps);

        System.out.println("def = " + defaultProps);
        System.out.println("def.b =" + defaultProps.getProperty("b"));
        System.out.println("def.d =" + defaultProps.getProperty("d"));

        System.out.println("app = " + applicationProps);
        System.out.println("app.b =" + applicationProps.getProperty("b"));
        System.out.println("app.d =" + applicationProps.getProperty("d"));

        // now load properties
        // from last invocation
        in = new FileInputStream("./tutorial/src/main/resources/platformProperties/app.properties");
        applicationProps.load(in);
        in.close();

        System.out.println("def = " + defaultProps);
        System.out.println("def.b =" + defaultProps.getProperty("b"));
        System.out.println("def.d =" + defaultProps.getProperty("d"));
        System.out.println("def.size =" + defaultProps.size());

        System.out.println("app = " + applicationProps);
        System.out.println("app.b =" + applicationProps.getProperty("b"));
        System.out.println("app.d =" + applicationProps.getProperty("d"));
        System.out.println("app.size =" + applicationProps.size());
        applicationProps.remove("d");
        System.out.println("app.d =" + applicationProps.getProperty("d"));
    }
}
