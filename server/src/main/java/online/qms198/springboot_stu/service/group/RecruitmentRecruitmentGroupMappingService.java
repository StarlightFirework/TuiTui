package online.qms198.springboot_stu.service.group;

import online.qms198.springboot_stu.pojo.group.RecruitmentRecruitmentGroupMapping;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.repository.group.RecruitmentGroupRepository;
import online.qms198.springboot_stu.repository.group.RecruitmentRecruitmentGroupMappingRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Service
public class RecruitmentRecruitmentGroupMappingService implements IRecruitmentRecruitmentGroupMappingService{

    @Autowired
    RecruitmentRecruitmentGroupMappingRepository recruitmentRecruitmentGroupMappingRepository;

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Autowired
    RecruitmentGroupRepository recruitmentGroupRepository;
    @Override
    @Transactional
    public void batchAddRecruitmentGroupMapping(List<Integer> groupAccounts , Integer recruitmentId){
        Recruitment recruitment = recruitmentRepository.findByRecruitmentId(recruitmentId);
        for (Integer groupAccount : groupAccounts) {
            recruitmentRecruitmentGroupMappingRepository.save(new RecruitmentRecruitmentGroupMapping(null,recruitment,recruitmentGroupRepository.findByGroupAccount(groupAccount),0));
        }
    }

}
