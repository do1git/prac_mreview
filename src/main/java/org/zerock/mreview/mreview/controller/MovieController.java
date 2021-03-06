package org.zerock.mreview.mreview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.mreview.dto.MovieDTO;
import org.zerock.mreview.mreview.dto.PageRequestDTO;
import org.zerock.mreview.mreview.service.MovieService;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
@Log4j2
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/")
    public String intro(){

        return "redirect:/movie/list";
    }


    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(Long member_id, MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO: " + movieDTO);
        System.out.println("member_id = " + member_id);
        Long mno = movieService.register(movieDTO,member_id);
        redirectAttributes.addFlashAttribute("msg", mno);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);

        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mno: " + mno);

        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);
    }

    @PostMapping("/modify")
    public String modify(long mno, MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("modify movie");

        movieService.modify(movieDTO);
        return "redirect:/movie/read?mno="+mno;

    }
}
