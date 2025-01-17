package online.qms198.springboot_stu.controller;

import online.qms198.springboot_stu.dto.tag.TagClassificationWithTagsDto;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.pojo.tag.TagClassification;
import online.qms198.springboot_stu.repository.tag.TagClassificationMappingRepository;
import online.qms198.springboot_stu.service.ITagClassificationService;
import online.qms198.springboot_stu.service.TagClassificationMappingService;
import online.qms198.springboot_stu.service.TagClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag-classifications")
//@CrossOrigin(
//        origins = {"http://localhost:3000", "https://qms198.online", "http://117.72.104.77"},
//        allowedHeaders = {"Authorization", "Content-Type"},
//        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT},
//        allowCredentials = "true",
//        exposedHeaders = {"Authorization"}
//)
public class TagClassificationController {

    @Autowired
    private TagClassificationService tagClassificationService;
    @Autowired
    private TagClassificationMappingService tagClassificationMappingService;

    // 新增 TagClassification
    @PostMapping("/create")
    public ResponseEntity<ResponseMessage<TagClassification>> createTagClassification(@Validated @RequestBody TagClassification tagClassification) {
        TagClassification createdTagClassification = tagClassificationService.createTagClassification(tagClassification);
        return ResponseEntity.ok(ResponseMessage.success(createdTagClassification));
    }

    // 更新 TagClassification
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage<TagClassification>> updateTagClassification(@PathVariable Long id, @Validated @RequestBody TagClassification tagClassification) {
        tagClassification.setId(id); // 设置要更新的 ID
        TagClassification updatedTagClassification = tagClassificationService.updateTagClassification(tagClassification);
        return ResponseEntity.ok(ResponseMessage.success(updatedTagClassification));
    }

    // 删除 TagClassification
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage<String>> deleteTagClassification(@PathVariable Long id) {
        tagClassificationService.deleteTagClassification(id);
        return ResponseEntity.ok(ResponseMessage.success("TagClassification deleted successfully."));
    }

    // 查询所有 TagClassification
    @GetMapping("/get/all")
    public ResponseEntity<ResponseMessage<List<TagClassification>>> getAllTagClassifications() {
        List<TagClassification> tagClassifications = tagClassificationService.getAllTagClassifications();
        return ResponseEntity.ok(ResponseMessage.success(tagClassifications));
    }

    // 根据 ID 查询单个 TagClassification
    @GetMapping("/get/id")
    public ResponseEntity<ResponseMessage<TagClassificationWithTagsDto>> getTagClassificationById(@RequestParam Long id) {
        // 获取 TagClassification 信息
        TagClassification tagClassification = tagClassificationService.getTagClassificationById(id);

        // 获取该大类标签对应的小标签列表

        List<Tag> tags = tagClassificationMappingService.findTagsByClassificationIdAndStatusNot(id);

        // 包装返回结果
        TagClassificationWithTagsDto response = new TagClassificationWithTagsDto(tagClassification, tags);

        return ResponseEntity.ok(ResponseMessage.success(response));
    }

}
