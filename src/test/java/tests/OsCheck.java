package tests;

import java.util.Locale;

public class OsCheck {

    protected static OSType detectedOS;

    public enum OSType {
        WINDOWS, MAC_OS, LINUX, OTHER
    }

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MAC_OS;
            } else if (OS.indexOf("win") >= 0) {
                detectedOS = OSType.WINDOWS;
            } else if (OS.indexOf("nux") >= 0) {
                detectedOS = OSType.LINUX;
            } else {
                detectedOS = OSType.OTHER;
            }
        }
        return detectedOS;
    }
}
