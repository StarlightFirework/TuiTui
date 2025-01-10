package online.qms198.springboot_stu.controller;

import online.qms198.springboot_stu.pojo.tag.TagClassification;
import online.qms198.springboot_stu.service.ITagClassificationService;
import online.qms198.springboot_stu.service.TagClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag-classifications")
@CrossOrigin(
        origins = {"http://localhost:3000", "https://qms198.online"},
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS},
        allowCredentials = "true",
        exposedHeaders = {"Authorization"}
)public class TagClassificationController {


    @Autowired
    private TagClassificationService tagClassificationService;

    // 新增 TagClassification
    @PostMapping
    public ResponseEntity<TagClassification> createTagClassification(@Validated @RequestBody TagClassification tagClassification) {
        TagClassification createdTagClassification = tagClassificationService.createTagClassification(tagClassification);
        return ResponseEntity.ok(createdTagClassification);
    }

    // 更新 TagClassification
    @PutMapping("/{id}")
    public ResponseEntity<TagClassification> updateTagClassification(@PathVariable Long id, @Validated @RequestBody TagClassification tagClassification) {
        tagClassification.setId(id); // 设置要更新的 ID
        TagClassification updatedTagClassification = tagClassificationService.updateTagClassification(tagClassification);
        return ResponseEntity.ok(updatedTagClassification);
    }

    // 删除 TagClassification
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTagClassification(@PathVariable Long id) {
        tagClassificationService.deleteTagClassification(id);
        return ResponseEntity.ok("TagClassification deleted successfully.");
    }

    // 查询所有 TagClassification
    @GetMapping
    public ResponseEntity<List<TagClassification>> getAllTagClassifications() {
        List<TagClassification> tagClassifications = tagClassificationService.getAllTagClassifications();
        return ResponseEntity.ok(tagClassifications);
    }

    // 根据 ID 查询单个 TagClassification
    @GetMapping("/{id}")
    public ResponseEntity<TagClassification> getTagClassificationById(@PathVariable Long id) {
        TagClassification tagClassification = tagClassificationService.getTagClassificationById(id);
        return ResponseEntity.ok(tagClassification);
    }
}
