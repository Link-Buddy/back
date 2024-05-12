package com.linkbuddy.domain.category;

import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
    List<CategoryDto.PrivateCategory> privateCategories = categoryService.findMyPrivateCategories();

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(privateCategories)
            .build());
  }

  @GetMapping("buddy")
  public ResponseEntity getMyBuddyCategory(@RequestParam("buddyId") Long buddyId) throws Exception {
    List<CategoryDto.BuddyCategory> boddyCategories = categoryService.findMyBuddyCategoriesByBuddyId(buddyId);

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
  public ResponseEntity createBuddyCategory(@RequestBody CategoryDto.CreateBuddy buddyCategoryDto) throws Exception {

    Category newCategory = categoryService.createBuddyCategory(buddyCategoryDto);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.CreateBuddy(newCategory))
            .build());
  }

  @PutMapping("{id}")
  public ResponseEntity updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto.Update updateDto) throws Exception {

    Category updatedCategory = categoryService.updateCategory(id, updateDto);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.Update(updatedCategory))
            .build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteCategory(@PathVariable("id") Long id) throws Exception {

    categoryService.deleteCategory(id);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data("")
            .build());
  }

}
