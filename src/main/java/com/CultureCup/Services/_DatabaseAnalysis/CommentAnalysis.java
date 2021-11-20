package com.CultureCup.Services._DatabaseAnalysis;

import com.CultureCup.DTO.Comment.Response.CommentListItem;
import com.CultureCup.Entities.Comment;
import com.CultureCup.Exceptions.CommentNotFoundException;
import com.CultureCup.Repositories.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentAnalysis {

    static Logger logger = LoggerFactory.getLogger(CommentAnalysis.class);

    public static Comment findCommentByCommentId(CommentRepository commentRepository, Long commentId) {

        Optional<Comment> comment = commentRepository.findCommentByCommentId(commentId);
        if (comment.isEmpty()) {
            logger.error("Comment with the given ID was not found: " + "\"" + commentId + "\"");
            throw new CommentNotFoundException("Komentarz o podanym ID nie został odnaleziony!");
        }

        return comment.get();
    }

    public static List<CommentListItem> findAllByMovieId(CommentRepository commentRepository, Long movieId) {

        List<Comment> commentList = commentRepository.findAllByMovieId(movieId);

        return commentList.stream().map(comment -> CommentListItem.builder()
                .commentId(comment.getCommentId())
                .username(comment.getUsername())
                .profileImage(comment.getProfileImage())
                .addingDate(comment.getAddingDate())
                .addingTime(comment.getAddingTime())
                .info(comment.getInfo())
                .likeNumber(comment.getLikeNumber())
                .unlikeNumber(comment.getUnlikeNumber())
                .build()).collect(Collectors.toList());
    }

    public static List<CommentListItem> findAllByGameId(CommentRepository commentRepository, Long gameId) {

        List<Comment> commentList = commentRepository.findAllByGameId(gameId);

        return commentList.stream().map(comment -> CommentListItem.builder()
                .commentId(comment.getCommentId())
                .username(comment.getUsername())
                .profileImage(comment.getProfileImage())
                .addingDate(comment.getAddingDate())
                .addingTime(comment.getAddingTime())
                .info(comment.getInfo())
                .likeNumber(comment.getLikeNumber())
                .unlikeNumber(comment.getUnlikeNumber())
                .build()).collect(Collectors.toList());
    }
}
