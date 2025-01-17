package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentPageDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentStatisticsDto;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;

import java.util.List;

public interface IRecruitmentStatisticsService {
    // 批量增加查询次数
    public void batchUpdateQueryCount(List<Integer> recruitmentIds);
    // 增加查看次数
    public void updateViewCount(Integer recruitmentId);

    //添加收藏
    public void addCollection(RecruitmentStatisticsDto recruitmentStatisticsDto);

    // 取消收藏
    public void cancelCollection(RecruitmentStatisticsDto recruitmentStatisticsDto);

    // 查询收藏次数
    public Integer findCollectionCount(Integer recruitmentId);

    // 获取用户收藏的招聘信息
    public RecruitmentPage findCollectionRecruitments(RecruitmentPageDto recruitmentPageDto);
}
