package org.zerock.mreview.mreview.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class UploadTestController {

    @GetMapping("/uploadEx")
    public void uploadEx(){
       log.info("intro upload");
    }
}
