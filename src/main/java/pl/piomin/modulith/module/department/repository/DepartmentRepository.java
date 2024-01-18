package pl.piomin.modulith.module.department.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.piomin.modulith.module.department.DepartmentDTO;
import pl.piomin.modulith.module.department.model.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    List<Department> findByOrganizationId(Long organizationId);

    void deleteByOrganizationId(Long organizationId);
}
