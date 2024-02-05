import java.util.List;

public class RunePage {
    private List<Integer> runes;
    private int primaryStyleId;
    private int substyleId;


    public RunePage(List<Integer> runes, int primaryStyleId, int substyleId) {
        this.runes = runes;
        this.primaryStyleId = primaryStyleId;
        this.substyleId = substyleId;
    }

    public List<Integer> getRunes() {
        return runes;
    }

    public int getPrimaryStyleId() {
        return primaryStyleId;
    }

    public int getSubstyleId() {
        return substyleId;
    }
}