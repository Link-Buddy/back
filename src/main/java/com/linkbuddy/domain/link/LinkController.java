package com.linkbuddy.domain.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/links")
    public List<Link> getMyLinks(){
        return linkService.findAll();
    }
    @PostMapping("/links")
    public Link createLink(@RequestBody Link link){

        return linkService.createLink(link);
    }
}
