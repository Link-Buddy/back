package com.linkbuddy.domain.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
    @Autowired
    private LinkRepository linkRepository;
    public List<Link> findAll(){
        return linkRepository.findAll();
    }
    public Link createLink(Link link){
        return linkRepository.save(link);
    }
}
