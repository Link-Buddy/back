package com.linkbuddy.domain.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.linkbuddy.domain.Category
 * fileName       : CategoryController
 * author         : yl951
 * date           : 2024-04-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-11        yl951       최초 생성
 */

@RequestMapping("link-groups")
@RestController
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

//  @GetMapping
//  public ResponseEntity getMyLinkGroup() {
//    List<LinkGroup> linkgroups = linkGroupService.
//  }
}
