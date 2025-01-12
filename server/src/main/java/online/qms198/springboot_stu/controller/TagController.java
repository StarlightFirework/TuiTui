package online.qms198.springboot_stu.controller;

import online.qms198.springboot_stu.dto.tag.TagClassificationDto;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.service.recruitment.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(
        origins = {"http://localhost:3000", "https://qms198.online", "http://117.72.104.77:8848"},
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT},
        allowCredentials = "true",
        exposedHeaders = {"Authorization"}
)
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // 添加Tag
    @PostMapping("/create")
    public ResponseEntity<ResponseMessage<Tag>> createTag(@RequestBody TagClassificationDto tagClassificationDto) {
        Tag createdTag = tagService.createTag(tagClassificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.success(createdTag));
    }

    // 更新Tag
    @PostMapping("/update")
    public ResponseEntity<ResponseMessage<Tag>> updateTag(@RequestBody TagClassificationDto tagClassificationDto) {
        Tag updatedTag = tagService.updateTag(tagClassificationDto);
        return ResponseEntity.ok(ResponseMessage.success(updatedTag));
    }

    // 删除Tag
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage<Void>> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();  // 由于没有返回内容, 也可以使用 ResponseMessage.success(null) 来标明删除成功
    }

    // 根据ID查询Tag
    @GetMapping("/get/id")
    public ResponseEntity<ResponseMessage<Tag>> GetTagById(@RequestParam Long id) {
        Tag tag = tagService.getTagById(id);
        return ResponseEntity.ok(ResponseMessage.success(tag));
    }

    // 模糊搜索Tag
    @GetMapping("/search")
    public ResponseEntity<ResponseMessage<List<Tag>>> searchTags(@RequestParam String keyword) {
        List<Tag> tags = tagService.searchTagsByName(keyword);
        return ResponseEntity.ok(ResponseMessage.success(tags));
    }

}
