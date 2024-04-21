package com.linkbuddy.domain.category;

import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

@RequestMapping("categories")
@RestController
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @GetMapping("my")
  public ResponseEntity getMyPrivateCategory() throws Exception {
    //TODO : Tuple이 아니라 PrivateCategoryDTO로 변경
    List<Tuple> privateCategories = categoryService.findMyPrivateCategories();

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(privateCategories)
            .build());
  }

  @GetMapping("buddy")
  public ResponseEntity getMyPublicCategory() throws Exception {
    //TODO : Tuple이 아니라 PublicCategoryDTO로 변경
    List<Tuple> boddyCategories = categoryService.findMyBuddyCategories();

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(boddyCategories)
            .build());
  }

  @PostMapping("/my")
  public ResponseEntity createPrivateCategory(@RequestBody CategoryDto.CreatePrivate privateDto) throws Exception {


    Category newCategory = categoryService.createPrivateCategory(privateDto);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.CreatePrivate(newCategory))
            .build());
  }

  @PostMapping("/buddy")
  public ResponseEntity createPublicCategory(@RequestBody CategoryDto.CreatePublic publicDto) throws Exception {

    Category newCategory = categoryService.createBuddyCategory(publicDto);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.CreatePublic(newCategory))
            .build());
  }

  @PutMapping("{id}")
  public ResponseEntity updateLink(@PathVariable("id") Long id, @RequestBody CategoryDto.Update updateDto) throws Exception {

    Category updatedCategory = categoryService.updateCategory(id, updateDto);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.Update(updatedCategory))
            .build());
  }
}
