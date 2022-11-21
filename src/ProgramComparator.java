import java.util.Comparator;

public class ProgramComparator implements Comparator<Program> {

    @Override
    public int compare(Program o1, Program o2) {
        if (o1.airing == Airing.AIRING && o2.airing == Airing.AIRING) {
            return o1.title.compareTo(o2.title);
        }
        if (o1.airing == Airing.AIRING && o2.airing == Airing.AIRED) {
            return -1;
        }
        if (o1.airing == Airing.AIRED && o2.airing == Airing.AIRING) {
            return 1;
        }
        if (o1.airing == Airing.FUTURE && o2.airing == Airing.FUTURE) {
            return o1.title.compareTo(o2.title);
        }
        if (o1.airing == Airing.FUTURE) {
            return 1;
        }
        if (o2.airing == Airing.FUTURE) {
            return -1;
        }
        return o1.title.compareTo(o2.title);
    }
}
