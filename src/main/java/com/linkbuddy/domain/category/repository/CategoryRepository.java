package com.linkbuddy.domain.category.repository;

import com.linkbuddy.global.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.linkbuddy.domain.linkGroup.repository
 * fileName       : CategoryGroupRepository
 * author         : yl951
 * date           : 2024-04-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-13        yl951       최초 생성
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustomRepository {
}
