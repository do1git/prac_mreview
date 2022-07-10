package org.zerock.mreview.mreview.service;


import org.zerock.mreview.mreview.dto.ReviewDTO;
import org.zerock.mreview.mreview.entity.Member;
import org.zerock.mreview.mreview.entity.Movie;
import org.zerock.mreview.mreview.entity.Review;

import java.util.List;

public interface ReviewService  {

    //그 영화의 모든 리뷰가져옴
    List<ReviewDTO> getListOfMovie(Long mno);

    //영화리뷰추가
    Long register(ReviewDTO movieReviewDTO);

    void modify(ReviewDTO movieReviewDTO);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO movieReviewDTO) {

        Review movieReview = Review.builder().reviewnum(movieReviewDTO.getReviewnum()).movie(Movie.builder().mno(movieReviewDTO.getMno()).build())
                .member(Member.builder().mid(movieReviewDTO.getMid()).build()).grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText()).build();

        return movieReview;
    }

    default ReviewDTO entityToDto(Review review) {

        ReviewDTO dto = ReviewDTO.builder().reviewnum(review.getReviewnum()).mno(review.getMovie().getMno()).mid(review.getMember().getMid())
                .nickName(review.getMember().getNickname()).email(review.getMember().getEmail())
                .grade(review.getGrade()).text(review.getText())
                .regDate(review.getRegDate()).modDate(review.getModDate()).build();

        return dto;
    }
}
