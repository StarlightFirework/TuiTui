package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.Recruitment;
import online.qms198.springboot_stu.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {

    //分页查询
//    @Query(value = "select * from recruitment where status = 1", nativeQuery = true);
    public Page<Recruitment> findByStatus(Integer status, Pageable pageable);
    //通过id查询
    public Recruitment findByRecruitmentId(Integer recruitmentId);

}
