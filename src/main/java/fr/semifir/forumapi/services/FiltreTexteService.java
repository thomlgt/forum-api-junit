package fr.semifir.forumapi.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FiltreTexteService {

    List<String> motsBannis;
    String chemin = "C:\\Dev\\Langages\\Java\\forum-api\\src\\assets\\insults.txt";

    public FiltreTexteService() throws IOException {
         try (Stream<String> lines = Files.lines(Paths.get(this.chemin))) {
            this.motsBannis = lines.collect(Collectors.toList());
        };
    }

    public String filtrerTexte(String texte) {
        String[] mots = texte.split(" ");
        for (int i = 0; i < mots.length; i++) {
            if(motsBannis.contains(mots[i].toLowerCase())) {
                mots[i] = "*****";
            }
        }
        texte = String.join(" ", mots);
        return texte;
    }
}
