package internals.formatters;

public class dataformatter {
    final public String TXRESET = "\u001B[0m";
    final public String RED = "\u001B[31m";
    final public String GREEN = "\u001B[32m";
    final public String YELLOW = "\u001B[33m";

    final public String BOLD = "\u001B[1m";

    final public String BG_RESET = "\u001B[0m";
    final public String BG_RED = "\u001B[31m";
    final public String BG_GREEN = "\u001B[32m";
    final public String BG_YELLOW = "\u001B[33m";
    final public String BG_BLACK = "\u001B[40m";
    final public String BG_WHITE = "\u001B[47m";

    public static void print(String value)
    {
        System.out.printf("\nConsole Out: %s", value);
    }

    public static String ByteToHexadecimal(byte[] byteArray)
    {
        String hex = "";
 
        // Iterating through each byte in the array
        for (byte i : byteArray) {
            hex += String.format("%02X", i);
        }

        return hex;
    }
    public static byte[] hexStringToByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
