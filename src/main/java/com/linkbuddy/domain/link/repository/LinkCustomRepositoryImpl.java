package com.linkbuddy.domain.link.repository;

import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.entity.QLink;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
@RequiredArgsConstructor
public class LinkCustomRepositoryImpl implements LinkCustomRepository {
  private final JPAQueryFactory query;
  QLink link = QLink.link;

  @Override
  public Link findOneActive(Long id) {
    Link result = query.selectFrom(link).where(link.id.eq(id)
            .and(link.deleteTf.eq(false))).fetchOne();
    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found with id: " + id);
    }
    return result;
  }

}