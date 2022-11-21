public class Program {
    String title;
    int currentEp;
    int totalEp;
    Airing airing;


    public Program(String title, int currentEp, int totalEp, Airing airing) {
        this.title = title;
        this.currentEp = currentEp;
        this.totalEp = totalEp;
        this.airing = airing;
    }

    public Program(Program p) {
        title = p.title;
        currentEp = p.currentEp;
        totalEp = p.totalEp;
        airing = p.airing;
    }

    public void incrementEpisode() {
        if (currentEp < totalEp) {
            currentEp++;
        }
    }

    public void editData(String title, int currentEp, int totalEp, Airing airing, boolean airingEdited) {
        title.trim();
        if (!title.isEmpty()) {
            this.title = title;
        }
        if (currentEp > -1) {
            this.currentEp = currentEp;
        }
        if (totalEp > -1) {
            this.totalEp = totalEp;
            if (this.totalEp < this.currentEp) {
                this.currentEp = this.totalEp;
            }
        }
        if (airingEdited) {
            this.airing = airing;
        }
    }

    public String toString() {
        if (airing == Airing.AIRING) {
            return title + " " + currentEp + "/" + totalEp + " Airing";
        }
        return title + " " + currentEp + "/" + totalEp;
    }

    public String getTitle() {
        return title;
    }

    public void complete() {
        currentEp = totalEp;
        airing = Airing.AIRED;
    }

    public String getAiring() {
        switch (airing) {
            case AIRED:
                return "AIRED";
            case AIRING:
                return "AIRING";
            case FUTURE:
                return "FUTURE";
        }
        return "Aired";
    }
}

