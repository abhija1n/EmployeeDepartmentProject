package com.assesment.spring.EmployeDepartmentService.repository;

;
import com.assesment.spring.EmployeDepartmentService.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query(value = """
            SELECT * FROM employee WHERE joining_Date <= :requestDate AND leaving_Date >= :requestDate1
            """, nativeQuery = true)
    List<Employee> findByJoiningDateBeforeAndExitDateAfter(@Param("requestDate") String requestDate,
                                                           @Param("requestDate1") String requestDate1);

}
