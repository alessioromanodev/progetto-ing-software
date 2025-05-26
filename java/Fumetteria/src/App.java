import entity.Fumetto;

public class App {
    public static void main(String[] args) throws Exception {
        byte[] immagineCopertina = new byte[] { 72, 101, 108, 108, 111 };
        Fumetto fumetto = new Fumetto(null, "Batman", "2022", "Batman vs Joker", "Supereroi", "Star comics", immagineCopertina, "Fumetto di Batman", 7.10, 5);
        fumetto.saveFumetto();
    }
}
