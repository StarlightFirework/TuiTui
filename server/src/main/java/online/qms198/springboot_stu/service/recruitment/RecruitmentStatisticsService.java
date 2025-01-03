package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.repository.RecruitmentStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentStatisticsService implements IRecruitmentStatisticsService{

    @Autowired
    private RecruitmentStatisticsRepository recruitmentStatisticsRepository;

    @Override
    public void batchUpdateQueryCount(List<Integer> recruitmentIds) {
        for(Integer recruitmentId : recruitmentIds){
            recruitmentStatisticsRepository.updateQueryCount(recruitmentId);
        }
    }
}