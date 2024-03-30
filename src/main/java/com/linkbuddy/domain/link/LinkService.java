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

  public Link findOneActive(Long id) throws Exception {
    try {
      return linkRepository.findOneActive(id).orElseThrow(() -> new IllegalArgumentException("Active Link not found." + id));
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public Link save(Link link) {
    linkRepository.save(link);
    return link;
  }

  public Link save(LinkDto.Create linkDto) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;

      Link newLink = Link.builder()
              .name(linkDto.getName())
              .description(linkDto.getDescription())
              .linkUrl(linkDto.getLinkUrl())
              .linkGroupId(linkDto.getLinkGroupId())
              .userId(userId)
              .build();

      return linkRepository.save(newLink);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }

  }

  public Link update(Long id, LinkDto.Update linkDto, Long userId) throws Exception {
    try {

      Link existLink = findOneActive(id);

      if (!existLink.isHost(userId)) {
        throw new IllegalArgumentException("삭제 권한 없음"); //공통 예외?
      }

      existLink.updateLink(linkDto);
      return linkRepository.save(existLink);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public void delete(Long id, Long userId) throws Exception {
    try {
      Link existLink = findOneActive(id);

      if (!existLink.isHost(userId)) {
        throw new IllegalArgumentException("삭제 권한 없음"); //공통 예외?
      }

      existLink.setDeleteTf(true);
      linkRepository.save(existLink);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }
}
