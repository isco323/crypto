
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class NGrammCounter {
    private final List<File> fileNames;
    private final Integer n;
    public final DictCreator dictCreator;

    public DictCreator getDictCreator() {
        return this.dictCreator;
    }

    public NGrammCounter(List<File> fileNames, Integer n, DictCreator dictCreator) {
        this.fileNames = fileNames;
        this.n = n;
        this.dictCreator = dictCreator;
    }

    private static List<String> splitWordOnNGramms(String word, Integer n) {
        List<String> result = new ArrayList();

        for(int i = 0; i < word.length() - (n - 1); ++i) {
            result.add(word.substring(i, i + n));
        }

        return result;
    }

    public NGrammRaw run(NGrammRaw nGramm) throws FileNotFoundException, UnsupportedEncodingException {
        ((Stream)this.fileNames.stream().parallel()).forEach((file) -> {
            Scanner fileReader;
            try {
                fileReader = new Scanner(new InputStreamReader(new FileInputStream(String.valueOf(file)), "UTF-8"));
            } catch (FileNotFoundException | UnsupportedEncodingException var5) {
                throw new RuntimeException(var5);
            }

            while(fileReader.hasNextLine()) {
                String[] words = fileReader.nextLine().toLowerCase().split(" ");
                Arrays.stream(words).forEach((word) -> {
                    word = Coder.removeSigns(word);
                    if (word.length() != 0) {
                        this.dictCreator.addWord(word);
                        List<String> splitedWords = splitWordOnNGramms(word, this.n);
                        Objects.requireNonNull(nGramm);
                        splitedWords.forEach(nGramm::addNGramm);
                    }

                });
            }

        });
        return nGramm;
    }

    public static NGrammRaw count(String txt, int n) {
        NGrammRaw nGramm = new NGrammRaw();
        txt = Coder.removeSigns(txt);
        if (txt.length() != 0) {
            List<String> splitedWords = splitWordOnNGramms(txt, n);
            Objects.requireNonNull(nGramm);
            splitedWords.forEach(nGramm::addNGramm);
        }

        return nGramm;
    }
}
