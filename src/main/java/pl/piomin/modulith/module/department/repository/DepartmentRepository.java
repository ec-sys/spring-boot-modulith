package pl.piomin.modulith.module.department.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.piomin.modulith.module.department.DepartmentDTO;
import pl.piomin.modulith.module.department.model.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    @Query("""
           SELECT new pl.piomin.modulith.module.department.DepartmentDTO(d.id, d.organizationId, d.name)
           FROM Department d
           WHERE d.id = :id
           """)
    DepartmentDTO findDTOById(Long id);

    @Query("""
           SELECT new pl.piomin.modulith.module.department.DepartmentDTO(d.id, d.organizationId, d.name)
           FROM Department d
           WHERE d.organizationId = :organizationId
           """)
    List<DepartmentDTO> findByOrganizationId(Long organizationId);

    void deleteByOrganizationId(Long organizationId);
}
