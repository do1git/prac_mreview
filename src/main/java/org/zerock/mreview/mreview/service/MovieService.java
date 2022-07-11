package org.zerock.mreview.mreview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.mreview.dto.*;
import org.zerock.mreview.mreview.entity.Movie;
import org.zerock.mreview.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDTO movieDTO, Long member_id);

    void modify(MovieDTO movieDTO);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) from Movie m left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);


    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {

        MovieDTO movieDTO = MovieDTO.builder().mno(movie.getMno()).title(movie.getTitle())
                .contents(movie.getContents())
                .regDate(movie.getRegDate()).modDate(movie.getModDate()).build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder().imgName(movieImage.getImgName()).path(movieImage.getPath()).uuid(movieImage.getUuid()).build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }


    //map타입으로 변환
    default Map<String, Object> dtoTOEntity(MovieDTO movieDTO){
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder().mno(movieDTO.getMno()).title(movieDTO.getTitle())
                .contents(movieDTO.getContents()).member(movieDTO.getMember()).build();

        entityMap.put("movie", movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        System.out.println("imageDTOList = " + imageDTOList);
        if (imageDTOList != null && imageDTOList.size() > 0) {
            //이해됨!! -> 이미지DTO를 하나하나 분해해서 이미지 리스트를 만듬
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder().path(movieImageDTO.getPath()).imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid()).movie(movie).build();
                return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }
        return entityMap;
    }

    MovieDTO getMovie(Long mno);
}
