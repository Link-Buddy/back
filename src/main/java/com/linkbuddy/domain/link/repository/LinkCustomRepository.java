package com.linkbuddy.domain.link.repository;

import com.linkbuddy.global.entity.Link;

public interface LinkCustomRepository {
  Link findOneActive(Long id);

}
