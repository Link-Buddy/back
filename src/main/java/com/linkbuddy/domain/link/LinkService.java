package com.linkbuddy.domain.link;

import com.linkbuddy.global.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {
  @Autowired
  private LinkRepository linkRepository;

  public List<Link> findAll() {
    return linkRepository.findAll();
  }

  private Link findById(Long id) {
    return linkRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Link not found." + id));
  }

  public Link findOneActive(Long id) {
    return linkRepository.findOneActive(id).orElseThrow(() -> new IllegalArgumentException("Active Link not found." + id));
  }

  public Link save(Link link) {
    linkRepository.save(link);
    return link;
  }

  public Link update(Long id, Link link) {
    return link;
  }

  public void delete(Long id, Long userId) {
    var existLink = findOneActive(id);

    if (!existLink.isHost(userId)) {
      throw new IllegalArgumentException("삭제 권한 없음"); //공통 예외?
    }

    existLink.setDeleteTf(true);
    linkRepository.save(existLink);

  }
}
