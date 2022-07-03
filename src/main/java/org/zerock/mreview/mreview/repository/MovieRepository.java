package org.zerock.mreview.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.mreview.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m, max(mi), avg(coalesce(r.grade,0) ), count(distinct r) from Movie m " +
            "left outer join MovieImage mi on mi.movie =m " +
            "left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);
}
