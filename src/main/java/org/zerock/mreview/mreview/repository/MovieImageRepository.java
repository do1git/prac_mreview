package org.zerock.mreview.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.mreview.mreview.entity.MovieImage;

@Repository
public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {
}
