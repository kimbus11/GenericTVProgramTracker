import java.util.Comparator;

public class CompleteProgramComparator implements Comparator<Program> {

    @Override
    public int compare(Program o1, Program o2) {
        return o1.title.compareTo(o2.title);
    }
}
