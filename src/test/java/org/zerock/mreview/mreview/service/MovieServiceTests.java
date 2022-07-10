package org.zerock.mreview.mreview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class MovieServiceTests {

    @Autowired
    private MovieService movieService;

    @Test
    public void test1(){
        movieService.getMovie(88L);
    }

}
