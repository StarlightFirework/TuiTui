package online.qms198.springboot_stu.service.recruitment;

import java.util.List;

public interface IRecruitmentStatisticsService {
    // 批量增加查询次数
    public void batchUpdateQueryCount(List<Integer> recruitmentIds);
    // 增加查看次数
    public void updateViewCount(Integer recruitmentId);

    //增加收藏次数
    public void updateCollectionCountplus(Integer recruitmentId);

    // 减少收藏次数
    public void updateCollectionCountMinus(Integer recruitmentId);
}
