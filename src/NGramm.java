
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NGramm {
    private Map<String, Double> ngramms = new HashMap();

    public NGramm(NGrammRaw nGrammRaw) {
        Iterator var2 = nGrammRaw.getnGramms().keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            this.ngramms.put(key, (double)(Integer)nGrammRaw.getnGramms().get(key) / (double)nGrammRaw.getnGrammsCounter() * 100.0);
        }

    }

    public Map<String, Double> getNgramms() {
        return this.ngramms;
    }

    public void setNgramms(Map<String, Double> ngramms) {
        this.ngramms = ngramms;
    }
}
