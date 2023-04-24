package internal;

public class IDGenerator {
    public Løbenummer løbenummer;
    public Database<Integer> database;

    public IDGenerator() {
        this.løbenummer = new Løbenummer();
    }

    String getProjektID(UgeDato dato) {
        return dato.getÅrstal() + "-" + løbenummer.getLøbenummer();
    }

    public Database<Integer> getDatabase() {
        return database;
    }

    public void setDatabase(Database<Integer> database) {
        this.database = database;
    }

    public Løbenummer getLøbenummer() {
        return løbenummer;
    }

    public void setLøbenummer(Løbenummer løbenummer) {
        this.løbenummer = løbenummer;
    }
}