package online.qms198.springboot_stu.pojo.group;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.dto.group.RecruitmentGroupDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recruitment_group")
public class RecruitmentGroup {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "group_account" , unique = true , nullable = false)
    private Integer groupAccount;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "creator_account")
    private String creatorAccount;

    @Column(name = "delete_status" , columnDefinition = "INT DEFAULT 0")
    private Integer deleteStatus;

    @Column(name = "visible_status" , columnDefinition = "INT DEFAULT 0")
    private Integer visibleStatus;

    public RecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto){
        this.creatorAccount = recruitmentGroupDto.getCreatorAccount();
        this.groupName = recruitmentGroupDto.getGroupName();
        this.groupAccount = recruitmentGroupDto.getGroupAccount();
        this.visibleStatus = recruitmentGroupDto.getVisibleStatus();
    }

    public RecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto , RecruitmentGroup recruitmentGroup){
        this.creatorAccount = recruitmentGroupDto.getCreatorAccount();
        this.groupName = recruitmentGroupDto.getGroupName();
        this.groupAccount = recruitmentGroupDto.getGroupAccount();
        this.visibleStatus = recruitmentGroupDto.getVisibleStatus();
        this.createTime = recruitmentGroup.getCreateTime();
        this.groupId = recruitmentGroup.getGroupId();
        this.deleteStatus = recruitmentGroup.getDeleteStatus();
    }

}
