package online.qms198.springboot_stu.controller;

import online.qms198.springboot_stu.dto.tag.TagClassificationDto;
import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.service.recruitment.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 标记请求处理类。接口方法返回对象，转换成json文本
@RequestMapping("/tags") // localhost:8088/user/，标记拦截user URL前缀地址类
@CrossOrigin(
        origins = {"http://localhost:3000", "https://qms198.online"},
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS},
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
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody TagClassificationDto tagClassificationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(tagClassificationDto));
    }

    // 更新Tag
    @PostMapping("/update")
    public ResponseEntity<Tag> updateTag(@RequestBody TagClassificationDto tagClassificationDto) {
        return ResponseEntity.ok(tagService.updateTag(tagClassificationDto));
    }

    // 删除Tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> GetTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    // 模糊搜索Tag
    @GetMapping("/search")
    public ResponseEntity<List<Tag>> searchTags(@RequestParam String keyword) {
        return ResponseEntity.ok(tagService.searchTagsByName(keyword));
    }

}
