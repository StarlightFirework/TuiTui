package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {

    Page<Recruitment> findAll(Pageable pageable);
}
