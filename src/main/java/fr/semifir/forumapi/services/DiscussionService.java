package fr.semifir.forumapi.services;

import fr.semifir.forumapi.models.Discussion;
import fr.semifir.forumapi.models.Message;
import fr.semifir.forumapi.repositories.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {

    private DiscussionRepository repository;
    private FiltreTexteService filtre;

    @Autowired
    public DiscussionService(DiscussionRepository repository, FiltreTexteService filtre) {
        this.repository = repository;
        this.filtre = filtre;
    }

    public List<Discussion> findAll() {
        return this.repository.findAll();
    }

    public Discussion findById(String id) {
        Optional<Discussion> discussion = this.repository.findById(id);
        if(discussion.isEmpty()) {
            throw new IllegalArgumentException("Message introuvable...");
        }
        return discussion.get();
    }

    public Discussion create(Discussion discussion) {
        discussion.setCreatedAt(new Date());
        discussion.setUpdatedAt(new Date());
        discussion.setMessages(new ArrayList<Message>());
        discussion.setTitre(filtre.filtrerTexte(discussion.getTitre()));
        return this.repository.save(discussion);
    }

    public void delete(String id) {
        this.repository.deleteById(id);
    }

    public Discussion addMessage(String idDiscussion, Message message) {
        Discussion discussion = this.findById(idDiscussion);
        message.setContenu(filtre.filtrerTexte(message.getContenu()));
        discussion.getMessages().add(message);
        return this.repository.save(discussion);
    }

    public Discussion deleteMessage(String idDiscussion, Message message) {
        Discussion discussion = this.findById(idDiscussion);
        discussion.getMessages().remove(message);
        return this.repository.save(discussion);
    }

    public void setFiltre(FiltreTexteService filtre) {
        this.filtre = filtre;
    }
}
