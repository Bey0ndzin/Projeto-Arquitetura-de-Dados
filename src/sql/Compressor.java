package sql;

public class Compressor {
    public static String Uncompress(String txt)
    {
        String output = "";
        for (int i = 0; i < txt.length(); i++)
        {
            char c = txt.charAt(i);
            output += (char)(c - ((txt.length() - i) * 777));
        }
        return output;
    }
}
