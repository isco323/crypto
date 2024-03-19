
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CharCounter {
    private final List<File> fileNames;
    private double countAll;
    private List<Integer> count;

    public CharCounter(List<File> fileNames) {
        this.fileNames = fileNames;
    }

    public List<Double> run(List<String> abc) {
        this.count = new ArrayList();

        for(int i = 0; i < abc.size(); ++i) {
            this.count.add(0);
        }

        ((Stream)this.fileNames.stream().parallel()).forEach((file) -> {
            Scanner fileReader;
            try {
                fileReader = new Scanner(new InputStreamReader(new FileInputStream(String.valueOf(file)), "UTF-8"));
            } catch (FileNotFoundException | UnsupportedEncodingException var5) {
                throw new RuntimeException(var5);
            }

            while(fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                Arrays.stream(line.toLowerCase().split("")).forEach((i) -> {
                    synchronized(this.count) {
                        if (abc.contains(i)) {
                            ++this.countAll;
                            this.count.set(abc.indexOf(i), (Integer)this.count.get(abc.indexOf(i)) + 1);
                        }

                    }
                });
            }

        });
        return (List)this.count.stream().map((ix) -> {
            return (double)ix / this.countAll * 100.0;
        }).collect(Collectors.toList());
    }
}
