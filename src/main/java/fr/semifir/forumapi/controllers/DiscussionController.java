package fr.semifir.forumapi.controllers;

import fr.semifir.forumapi.models.Discussion;
import fr.semifir.forumapi.models.Message;
import fr.semifir.forumapi.repositories.DiscussionRepository;
import fr.semifir.forumapi.services.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService service;

    @GetMapping("")
    public List<Discussion> findAll() {
        return this.service.findAll();
    }

    @GetMapping("{id}")
    public Discussion findById(@PathVariable String id) {
        return this.service.findById(id);
    }

    @PostMapping("")
    public Discussion create(@RequestBody Discussion discussion) {
        return this.service.create(discussion);
    }

    @DeleteMapping("{id}/")
    public void delete(@PathVariable String id) {
        this.service.delete(id);
    }

    @PostMapping("{id}/messages")
    public Discussion addMessage(@PathVariable String id, @RequestBody Message message) {
        return this.service.addMessage(id, message);
    }

    @DeleteMapping("{id}/messages")
    public Discussion deleteMessage(@PathVariable String id, @RequestBody Message message) {
        return this.service.deleteMessage(id, message);
    }
}
