package org.zerock.mreview.mreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.mreview.dto.MovieDTO;
import org.zerock.mreview.mreview.dto.PageRequestDTO;
import org.zerock.mreview.mreview.dto.PageResultDTO;
import org.zerock.mreview.mreview.entity.Member;
import org.zerock.mreview.mreview.entity.Movie;
import org.zerock.mreview.mreview.entity.MovieImage;
import org.zerock.mreview.mreview.entity.Review;
import org.zerock.mreview.mreview.repository.MemberRepository;
import org.zerock.mreview.mreview.repository.MovieImageRepository;
import org.zerock.mreview.mreview.repository.MovieRepository;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor //for 생성자 주입
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO, Long member_id) {

        Map<String, Object> entityMap = dtoTOEntity(movieDTO);
        System.out.println("entityMap = " + entityMap);

        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
        System.out.println("movieImageList = " + movieImageList);

        Optional<Member> member = memberRepository.findById(member_id);
        movie.setMember(member.get());

        movieRepository.save(movie);
//무비랑 각각의 이미지를 따로저장하네
        int i=0;
        movieImageList.forEach(movieImage ->{
            System.out.println("movieImageList = " + movieImageList.get(i));
            imageRepository.save(movieImage);
        });
        return movie.getMno();
    }

    @Override
    public void modify(MovieDTO movieDTO){
        Optional<Movie> result = movieRepository.findById(movieDTO.getMno());

        if (result.isPresent()) {
            Movie movie = result.get();

            movie.setTitle(movieDTO.getTitle());
            movie.setContents(movieDTO.getContents());

            movieRepository.save(movie);
        }

    }


    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[],MovieDTO> fn =(arr->
            entitiesToDTO((Movie) arr[0], (List<MovieImage>) (Arrays.asList((MovieImage) arr[1])), (Double) arr[2], (Long) arr[3])
        );
        return new PageResultDTO<>(result, fn);
    }


    @Override
    public Page<Object[]> getListPage(Pageable pageable) {
        return null;
    }

    @Override
    public MovieDTO getMovie(Long mno) {

        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];


        return entitiesToDTO(movie,movieImageList,avg,reviewCnt);

    }
}
