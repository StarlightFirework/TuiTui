package online.qms198.springboot_stu.service.recruitment;

import java.util.List;

public interface IRecruitmentStatisticsService {
    public void batchUpdateQueryCount(List<Integer> recruitmentIds);
}
