package fr.semifir.forumapi.repositories;

import fr.semifir.forumapi.models.Discussion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscussionRepository extends MongoRepository<Discussion, String> {
}
