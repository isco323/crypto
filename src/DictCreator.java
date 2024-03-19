import java.util.HashSet;
import java.util.Set;

public class DictCreator {
    private Set<String> dict = new HashSet();

    public DictCreator() {
    }

    public void addWord(String word) {
        this.dict.add(word);
    }

    public Set<String> getDict() {
        return this.dict;
    }

    public void setDict(Set<String> dict) {
        this.dict = dict;
    }
}
