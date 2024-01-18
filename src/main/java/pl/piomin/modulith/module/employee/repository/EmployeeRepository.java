package pl.piomin.modulith.module.employee.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.piomin.modulith.module.employee.EmployeeDTO;
import pl.piomin.modulith.module.employee.model.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<EmployeeDTO> findByDepartmentId(Long departmentId);

    List<EmployeeDTO> findByOrganizationId(Long organizationId);

    void deleteByOrganizationId(Long organizationId);
}
