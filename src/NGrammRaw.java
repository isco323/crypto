
import java.util.HashMap;
import java.util.Map;

public class NGrammRaw {
    private Map<String, Integer> nGramms = new HashMap();
    private Integer nGrammsCounter = 0;

    public NGrammRaw() {
    }

    public void addNGramm(String nGramm) {
        synchronized(this.nGramms) {
            if (this.nGramms.containsKey(nGramm)) {
                this.nGramms.put(nGramm, (Integer)this.nGramms.get(nGramm) + 1);
            } else {
                this.nGramms.put(nGramm, 1);
            }

            Integer var3 = this.nGrammsCounter;
            this.nGrammsCounter = this.nGrammsCounter + 1;
        }
    }

    public Map<String, Integer> getnGramms() {
        return this.nGramms;
    }

    public void setnGramms(Map<String, Integer> nGramms) {
        this.nGramms = nGramms;
    }

    public Integer getnGrammsCounter() {
        return this.nGrammsCounter;
    }

    public void setnGrammsCounter(Integer nGrammsCounter) {
        this.nGrammsCounter = nGrammsCounter;
    }
}