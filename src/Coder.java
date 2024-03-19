
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Coder {
    public Coder() {
    }

    public static String removeSigns(String text) {
        List<String> abc = new ArrayList();
        abc.addAll(Consts.abcEngList);
        abc.addAll(Consts.abcRuList);
        abc.add(" ");
        StringBuilder sb = new StringBuilder();
        Stream var10000 = Arrays.stream(text.split(""));
        Objects.requireNonNull(abc);
        var10000 = var10000.filter(abc::contains);
        Objects.requireNonNull(sb);
        var10000.forEach(sb::append);
        return sb.toString();
    }

    public static String coderBlack(String text, String abc, String code) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < text.length(); ++i) {
            char var10001 = text.charAt(i);
            if (abc.contains("" + var10001)) {
                sb.append(code.charAt(abc.indexOf(text.charAt(i))));
            }
        }

        return sb.toString();
    }

    public static String coderGray(String text, String abc, String code) {
        StringBuilder sb = new StringBuilder();
        text = text.replace("--", " ");

        for(int i = 0; i < text.length(); ++i) {
            char var10001 = text.charAt(i);
            if (abc.contains("" + var10001)) {
                sb.append(code.charAt(abc.indexOf(text.charAt(i))));
            } else if (String.valueOf(text.charAt(i)).equals(".") || String.valueOf(text.charAt(i)).equals(" ")) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    public static String coderWhite(String text, String abc, String code) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < text.length(); ++i) {
            char var10001 = text.charAt(i);
            if (abc.contains("" + var10001)) {
                sb.append(code.charAt(abc.indexOf(text.charAt(i))));
            } else {
                sb.append(text.charAt(i));
            }
        }

        return sb.toString();
    }
}
