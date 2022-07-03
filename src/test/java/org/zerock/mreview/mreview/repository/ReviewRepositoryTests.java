package org.zerock.mreview.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.mreview.entity.Member;
import org.zerock.mreview.mreview.entity.Movie;
import org.zerock.mreview.mreview.entity.Review;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews(){

        IntStream.rangeClosed(1,200).forEach(i->{
            Long mno = (long) (Math.random() * 100) + 1;
            Movie movie = Movie.builder().mno(mno).build();

            Long mid = ((long) (Math.random() * 100) + 1);
            Member member = Member.builder().mid(mid).build();

            Review movieReview = Review.builder().member(member).movie(movie)
                    .grade((int) (Math.random() * 5) + 1).text("이 영화는 나의" + i + "번쨰 양화랄까..").build();

            reviewRepository.save(movieReview);
        });
    }

}
