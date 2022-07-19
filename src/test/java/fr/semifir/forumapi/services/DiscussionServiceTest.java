package fr.semifir.forumapi.services;

import fr.semifir.forumapi.models.Discussion;
import fr.semifir.forumapi.repositories.DiscussionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiscussionServiceTest {

    AutoCloseable closeable;
//    @Autowired
////    private FiltreTexteService filtre;
    @Mock
    private DiscussionRepository repository;
    @InjectMocks
    private DiscussionService service;

    List<Discussion> discussions = new ArrayList<Discussion>();

    @BeforeEach
    public void setup() throws IOException {
        closeable = MockitoAnnotations.openMocks(this);
        this.service.setFiltre(new FiltreTexteService());

        discussions.add(new Discussion("id1", "Coucou", "toto", new Date(), new Date(), new ArrayList<>()));
        discussions.add(new Discussion("id2", "Bonjour", "titi", new Date(), new Date(), new ArrayList<>()));
        discussions.add(new Discussion("id3", "Hello", "tata", new Date(), new Date(), new ArrayList<>()));
    }

    @AfterEach
    public void closeMocks() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Devrait insérer une discussion sans insulte")
    public void insertionDiscussionOkSansInsulte() {
        Discussion discussionDonne = new Discussion(null, "Hola", "toto", null, null, null);

        when(repository.save(any(Discussion.class))).thenAnswer(i -> {
            Discussion d = i.getArgument(0);
            d.setId("id4");
            return d;
        });

        Discussion discussionRecu = service.create(discussionDonne);

        assertNotNull(discussionRecu.getCreatedAt());
        assertNotNull(discussionRecu.getUpdatedAt());
        assertEquals(0, discussionRecu.getMessages().size());
        assertEquals("Hola", discussionRecu.getTitre());
    }

    @Test
    @DisplayName("Devrait insérer une discussion avec insultes filtrées")
    public void insertionDiscussionOkInsulte() {
        Discussion discussionDonne = new Discussion(null, "Con de merde", "toto", null, null, null);

        when(repository.save(any(Discussion.class))).thenAnswer(i -> {
            Discussion d = i.getArgument(0);
            d.setId("id4");
            return d;
        });

        Discussion discussionRecu = service.create(discussionDonne);

        assertNotNull(discussionRecu.getCreatedAt());
        assertNotNull(discussionRecu.getUpdatedAt());
        assertEquals(0, discussionRecu.getMessages().size());
        assertEquals("***** de *****", discussionRecu.getTitre());
    }

    @Test
    @DisplayName("Devrait récupérer une liste de discussions")
    public void findAllDiscussionsOk() {
        when(repository.findAll()).thenReturn(this.discussions);

        List<Discussion> discussionsRecus = service.findAll();

        assertEquals(3, discussionsRecus.size());
    }

    @Test
    @DisplayName("Devrait récupérer une discussion en fonction de son id")
    public void findByIdDiscussionsOk() {
        when(repository.findById(any(String.class))).thenReturn(Optional.of(this.discussions.get(2)));

        Discussion discussionRecu = service.findById("id3");

        assertEquals("Hello", discussionRecu.getTitre());
    }

    @Test
    @DisplayName("Devrait lever une exception en récupérant une discussion en fonction de son id")
    public void findByIdDiscussionsPasOk() {
        when(repository.findById(any(String.class))).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            Discussion discussionRecu = service.findById("id6");
        });
    }


}
