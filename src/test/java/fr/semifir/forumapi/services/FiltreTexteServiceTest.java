package fr.semifir.forumapi.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.imageio.IIOException;
import java.io.IOException;

public class FiltreTexteServiceTest {

    private FiltreTexteService filtreTexteService;

    @BeforeEach
    public void initialize() throws IOException {
        this.filtreTexteService = new FiltreTexteService();
    }

    @Test()
    @DisplayName("La liste des mots bannis devrait être initialisée")
    public void initBanWords() {
        assertEquals("", this.filtreTexteService.motsBannis.get(2420));
    }

    @Test()
    @DisplayName("La phrase avec une insulte doit être filtrée")
    public void filtreOkInsulte() {
        String texteDonne = "Connard que tu es...";
        String texteRecu = this.filtreTexteService.filtrerTexte(texteDonne);
        assertEquals("***** que tu es...", texteRecu);
    }

    @Test()
    @DisplayName("La phrase sans insulte ne doit pas être filtrée")
    public void filtreOkSansInsulte() {
        String texteDonne = "Bonjour à tous!";
        String texteRecu = this.filtreTexteService.filtrerTexte(texteDonne);
        assertEquals("Bonjour à tous!", texteRecu);
    }
}
