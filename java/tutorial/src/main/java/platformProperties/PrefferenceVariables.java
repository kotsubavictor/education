package platformProperties;

import java.util.prefs.Preferences;

public class PrefferenceVariables {
    private static final String PACKAGE = "com/platform/pref";

    // Preference keys for this package
    private static final String NUM_ROWS = "num_rows";
    private static final String NUM_COLS = "num_cols";

    public static void main(String[] args) {
        Preferences prefs = Preferences.userNodeForPackage(PrefferenceVariables.class);

        int numRows = prefs.getInt(NUM_ROWS, 40);
        int numCols = prefs.getInt(NUM_COLS, 80);

        System.out.println(numCols);
        System.out.println(numRows);

        System.out.println(Preferences.userRoot().absolutePath());
        System.out.println(Preferences.systemRoot().absolutePath());
        System.out.println(Preferences.systemRoot().node(PACKAGE).absolutePath());
    }
}
