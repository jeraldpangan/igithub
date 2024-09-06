package backend;

import java.util.ArrayList;

/** Stores data related to previous expressions */
public class History {
    /**
     * stores the calculations once on calculate is called
     */
    public static ArrayList<String> storeCalcs = new ArrayList<String>();

    /** Current length of the history list */
    public static int length = 0;
}
