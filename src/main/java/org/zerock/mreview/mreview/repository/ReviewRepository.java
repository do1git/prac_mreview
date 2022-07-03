package org.zerock.mreview.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview.mreview.entity.Review;


public interface ReviewRepository extends JpaRepository<Review, Long> {
}
