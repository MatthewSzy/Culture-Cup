package com.CultureCup.Repositories;

import com.CultureCup.Entities.Comment;
import com.CultureCup.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByCommentId(Long commentId);

    List<Comment> findAllByMovieId(Long movieId);

    List<Comment> findAllByGameId(Long gameId);
}
