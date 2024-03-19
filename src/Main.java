
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final List<String> abcList;
    private static final String abcString = "abcdefghijklmnopqrstuvwxyz";
    private static final int n = 2;

    public static void main(String[] args) throws IOException {
        hack();
    }

    private static void hack() throws FileNotFoundException, UnsupportedEncodingException {
        DictCreator dict = new DictCreator();
        long time = System.currentTimeMillis();
        File dir = new File("D:\\eng");
        List<File> files = List.of(dir.listFiles());
        NGrammRaw nGrammRaw = new NGrammRaw();
        NGrammCounter nGrammCounter = new NGrammCounter(files, 2, dict);
        nGrammRaw = nGrammCounter.run(nGrammRaw);
        NGramm standart = new NGramm(nGrammRaw);
        double sum = 0.0;

        double i;
        for(Iterator var10 = standart.getNgramms().values().iterator(); var10.hasNext(); sum += i) {
            i = (Double)var10.next();
        }

        CharCounter charCounter = new CharCounter(files);
        List<Double> charFreq = charCounter.run(abcList);
        double sumChars = 0.0;

        for(Iterator var14 = charFreq.iterator(); var14.hasNext(); sumChars += i) {
            i = (Double)var14.next();
        }

        System.out.println(charFreq.toString());
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(sum);
        System.out.println(sumChars);
        sum = 0.0;
        time = System.currentTimeMillis();
        File dirToDecode = new File("D:\\engDecode");
        List<File> englishFilesToDecode = List.of((File[])Objects.requireNonNull(dirToDecode.listFiles()));
        DictCreator trash = new DictCreator();
        NGrammRaw nGrammRawToDecode = new NGrammRaw();
        NGrammCounter nGrammCounterToDecode = new NGrammCounter(englishFilesToDecode, 2, trash);
        nGrammRawToDecode = nGrammCounterToDecode.run(nGrammRawToDecode);
        NGramm nGrammToDecode = new NGramm(nGrammRawToDecode);
        double sumToDecode = 0.0;


        for(Iterator var22 = nGrammToDecode.getNgramms().values().iterator(); var22.hasNext(); sum += i) {
            i = (Double)var22.next();
        }

        sumChars = 0.0;
        CharCounter charCounterToDecode = new CharCounter(englishFilesToDecode);
        List<Double> charFreqToDecode = charCounterToDecode.run(abcList);


        for(Iterator var24 = charFreqToDecode.iterator(); var24.hasNext(); sumChars += i) {
            i = (Double)var24.next();
        }

        System.out.println(System.currentTimeMillis() - time);
        System.out.println(sum);
        System.out.println(sumChars);
        System.out.println(charFreqToDecode.toString());
        StringBuilder textBuilder = new StringBuilder();

        Scanner fileReader;
        try {
            fileReader = new Scanner(new InputStreamReader(new FileInputStream((File)englishFilesToDecode.get(0)), "UTF-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException var50) {
            throw new RuntimeException(var50);
        }

        while(fileReader.hasNextLine()) {
            textBuilder.append(fileReader.nextLine());
        }

        String text = textBuilder.toString().toLowerCase();
        List<Double> charFreqToDecodeSorted = charFreqToDecode.stream().sorted().toList();
        List<String> key = new ArrayList();

        for(int k = 0; k < charFreqToDecode.size(); ++k) {
            String symb = (String)abcList.get(charFreqToDecode.indexOf(charFreqToDecodeSorted.get(k)));
            if (!key.contains(symb)) {
                key.add(symb);
            } else {
                Double current = (Double)charFreqToDecodeSorted.get(k);

                for(int j = 0; j < charFreqToDecode.size(); ++j) {
                    if (Objects.equals(charFreqToDecode.get(j), current)) {
                        String s = (String)abcList.get(j);
                        if (!key.contains(s)) {
                            key.add(s);
                            break;
                        }
                    }
                }
            }
        }

        Collections.reverse(key);
        System.out.println(key.toString());
        Double rating = nGrammsRating(standart, nGrammToDecode);
        Double oldRating = rating;
        int step = 1;

        while(true) {
            boolean findGoodNGrammRating = false;

            String word;
            for(int k = 0; k < abcList.size() && k + step < abcList.size(); ++k) {
                String tempChar = String.valueOf(key.get(k));
                key.set(k, (String)key.get(k + step));
                key.set(k + step, tempChar);
                word = Coder.coderBlack(text, keyToString(key), "abcdefghijklmnopqrstuvwxyz");
                nGrammToDecode = new NGramm(NGrammCounter.count(word, 2));
                rating = nGrammsRating(standart, nGrammToDecode);
                if (rating < oldRating) {
                    findGoodNGrammRating = true;
                    oldRating = rating;
                    step = 1;
                    break;
                }

                tempChar = String.valueOf(key.get(k));
                key.set(k, (String)key.get(k + step));
                key.set(k + step, tempChar);
            }

            if (!findGoodNGrammRating) {
                ++step;
                if (step >= abcList.size() - 1) {
                    DictCreator wrongWords = new DictCreator();
                    String[] var65 = Coder.coderGray(text, keyToString(key), "abcdefghijklmnopqrstuvwxyz").split(" ");
                    int var67 = var65.length;

                    String s;
                    for(int var69 = 0; var69 < var67; ++var69) {
                        s = var65[var69];
                        if (!dict.getDict().contains(s)) {
                            wrongWords.addWord(s);
                        }
                    }

                    wrongWords.getDict().remove("");
                    Map<String, Integer> charCount = new HashMap();
                    Iterator var68 = wrongWords.getDict().iterator();

                    while(var68.hasNext()) {
                        word = (String)var68.next();
                        List<String> chars = List.of(word.split(""));
                        Iterator var37 = chars.iterator();

                        while(var37.hasNext()) {
                            String c = (String)var37.next();
                            if (charCount.containsKey(c)) {
                                charCount.put(c, (Integer)charCount.get(c) + 1);
                            } else {
                                charCount.put(c, 1);
                            }
                        }
                    }

                    Map<Integer, String> charCountReversed = new HashMap();
                    Iterator var71 = charCount.keySet().iterator();

                    while(var71.hasNext()) {
                        s = (String)var71.next();
                        if (charCountReversed.containsKey(charCount.get(s))) {
                            Integer var10001 = (Integer)charCount.get(s);
                            Object var10002 = charCountReversed.get(charCount.get(s));
                            charCountReversed.put(var10001, (String)var10002 + s);
                        } else {
                            charCountReversed.put((Integer)charCount.get(s), s);
                        }
                    }

                    boolean flag = false;
                    Iterator var74 = charCountReversed.keySet().iterator();

                    while(var74.hasNext()) {
                        Integer iKey = (Integer)var74.next();
                        String[] var76 = ((String)charCountReversed.get(iKey)).split("");
                        int var39 = var76.length;

                        for(int var40 = 0; var40 < var39; ++var40) {
                            s = var76[var40];

                            for(int j = 0; j < abcList.size(); ++j) {
                                int index = abcList.indexOf(s);
                                String tempChar = String.valueOf(key.get(index));
                                key.set(index, (String)key.get(j));
                                key.set(j, tempChar);
                                DictCreator newWrongWords = new DictCreator();
                                String[] var46 = Coder.coderGray(text, keyToString(key), "abcdefghijklmnopqrstuvwxyz").split(" ");
                                int var47 = var46.length;

                                for(int var48 = 0; var48 < var47; ++var48) {
                                    String sc = var46[var48];
                                    if (!dict.getDict().contains(sc)) {
                                        newWrongWords.addWord(sc);
                                    }
                                }

                                newWrongWords.getDict().remove("");
                                if (newWrongWords.getDict().isEmpty()) {
                                    flag = true;
                                    break;
                                }

                                if (newWrongWords.getDict().size() < wrongWords.getDict().size()) {
                                    wrongWords.setDict(newWrongWords.getDict());
                                } else {
                                    tempChar = String.valueOf(key.get(index));
                                    key.set(index, (String)key.get(j));
                                    key.set(j, tempChar);
                                }
                            }
                        }

                        if (flag) {
                            break;
                        }
                    }

                    System.out.println(key);
                    System.out.println(Coder.coderWhite(text, keyToString(key), "abcdefghijklmnopqrstuvwxyz"));
                    return;
                }
            }
        }
    }

    private static String keyToString(List<String> key) {
        StringBuilder sb = new StringBuilder();
        Objects.requireNonNull(sb);
        key.forEach(sb::append);
        return sb.toString();
    }

    private static Double nGrammsRating(NGramm standart, NGramm decrypt) {
        Double result = 0.0;
        Iterator var3 = abcList.iterator();

        label38:
        while(var3.hasNext()) {
            String firstChar = (String)var3.next();
            Iterator var5 = abcList.iterator();

            while(true) {
                while(true) {
                    if (!var5.hasNext()) {
                        continue label38;
                    }

                    String secondChar = (String)var5.next();
                    String key = firstChar + secondChar;
                    if (!standart.getNgramms().containsKey(key) && decrypt.getNgramms().containsKey(key)) {
                        result = result + Math.pow((Double)decrypt.getNgramms().get(key), 2.0);
                    } else if (standart.getNgramms().containsKey(key) && decrypt.getNgramms().containsKey(key)) {
                        result = result + Math.pow((Double)decrypt.getNgramms().get(key) - (Double)standart.getNgramms().get(key), 2.0);
                    } else if (standart.getNgramms().containsKey(key) && !decrypt.getNgramms().containsKey(key)) {
                        result = result + Math.pow((Double)standart.getNgramms().get(key), 2.0);
                    }
                }
            }
        }

        return result;
    }

    static {
        abcList = Consts.abcEngList;
    }
}