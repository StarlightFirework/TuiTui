package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentGroupDto;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentGroup;
import online.qms198.springboot_stu.pojo.recruitment.UserRecruitmentGroupMapping;
import online.qms198.springboot_stu.repository.RecruitmentGroupRepository;
import online.qms198.springboot_stu.repository.UserGroupMappingRepository;
import online.qms198.springboot_stu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RecruitmentGroupService implements IRecruitmentGroupService{

    @Autowired
    RecruitmentGroupRepository recruitmentGroupRepository;

    @Autowired
    UserGroupMappingRepository userGroupMappingRepository;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public RecruitmentGroup addRecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto){

        Random random = new Random();

        Integer randomGroupAccount = 0;
        while(true){
            randomGroupAccount = random.nextInt(100000,99999999);
            if(recruitmentGroupRepository.findByGroupAccount(randomGroupAccount) == null){
                break;
            }
        }
        RecruitmentGroup recruitmentGroup = new RecruitmentGroup(recruitmentGroupDto);
        // 设置圈子创建时间
        recruitmentGroup.setCreateTime(LocalDateTime.now());
        // 设置圈子账号
        recruitmentGroup.setGroupAccount(randomGroupAccount);
        // 设置圈子删除状态
        recruitmentGroup.setDeleteStatus(0);

        userGroupMappingRepository.save(new UserRecruitmentGroupMapping(null,userService.getUserByUserAccount(recruitmentGroupDto.getCreatorAccount()),recruitmentGroup,2,0));
        return recruitmentGroupRepository.save(recruitmentGroup);
    }

    @Override
    public RecruitmentGroup editRecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto) throws Exception {
        RecruitmentGroup recruitmentGroupOld = recruitmentGroupRepository.findByGroupAccount(recruitmentGroupDto.getGroupAccount());
        if(recruitmentGroupOld == null){
            throw new Exception("修改招聘圈信息不存在或已删除,圈子账号： " + recruitmentGroupDto.getGroupAccount());
        }
        return recruitmentGroupRepository.save(new RecruitmentGroup(recruitmentGroupDto,recruitmentGroupOld));
    }

    @Override
    public void deleteRecruitmentGroup(Integer groupAccount) throws Exception {
        if(recruitmentGroupRepository.findByGroupAccount(groupAccount) == null){
            throw new Exception("删除的圈子账号不存在或已删除，圈子账号：" + groupAccount);
        }
        recruitmentGroupRepository.deleteByGroupAccount(groupAccount);
    }

    @Override
    public RecruitmentGroup getVisibleRecruitmentGroup(Integer groupAccount) throws Exception {
        RecruitmentGroup recruitmentGroup =  recruitmentGroupRepository.findVisibleRecruitmentByGroupAccount(groupAccount , 0);
        if(recruitmentGroup == null){
            throw new Exception("圈子账号不存在或已删除，圈子账号：" + groupAccount);
        }
        return recruitmentGroup;
    }

    @Override
    public RecruitmentGroup getRecruitmentGroupByAccount(Integer groupAccount) throws Exception {
        RecruitmentGroup recruitmentGroup =  recruitmentGroupRepository.findByGroupAccount(groupAccount);
        if(recruitmentGroup == null){
            throw new Exception("圈子账号不存在或已删除，圈子账号：" + groupAccount);
        }
        return recruitmentGroup;
    }
}
