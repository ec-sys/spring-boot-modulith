package pl.piomin.modulith.module.department.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import pl.piomin.modulith.module.OrganizationAddEvent;
import pl.piomin.modulith.module.OrganizationRemoveEvent;
import pl.piomin.modulith.module.department.DepartmentDTO;
import pl.piomin.modulith.module.department.DepartmentExternalAPI;
import pl.piomin.modulith.module.department.DepartmentInternalAPI;
import pl.piomin.modulith.module.department.mapper.DepartmentMapper;
import pl.piomin.modulith.module.department.model.Department;
import pl.piomin.modulith.module.department.repository.DepartmentRepository;
import pl.piomin.modulith.module.employee.EmployeeDTO;
import pl.piomin.modulith.module.employee.EmployeeInternalAPI;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentManagement implements DepartmentInternalAPI, DepartmentExternalAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentManagement.class);
    private final DepartmentRepository repository;
    private final EmployeeInternalAPI employeeInternalAPI;
    private final DepartmentMapper mapper;

    public DepartmentManagement(DepartmentRepository repository,
                                EmployeeInternalAPI employeeInternalAPI,
                                DepartmentMapper mapper) {
        this.repository = repository;
        this.employeeInternalAPI = employeeInternalAPI;
        this.mapper = mapper;
    }

    @Override
    public DepartmentDTO getDepartmentByIdWithEmployees(Long id) {
        Department department = repository.findById(id).get();
        DepartmentDTO dto = new DepartmentDTO(department.getId(), department.getOrganizationId(), department.getName());
        List<EmployeeDTO> dtos = employeeInternalAPI.getEmployeesByDepartmentId(id);
        dto.employees().addAll(dtos);
        return dto;
    }

    @ApplicationModuleListener
     void onNewOrganizationEvent(OrganizationAddEvent event) {
        LOG.info("onNewOrganizationEvent(orgId={})", event.getId());
        add(new DepartmentDTO(null, event.getId(), "HR"));
        add(new DepartmentDTO(null, event.getId(), "Management"));
    }

    @ApplicationModuleListener
    void onRemovedOrganizationEvent(OrganizationRemoveEvent event) {
        LOG.info("onRemovedOrganizationEvent(orgId={})", event.getId());
        repository.deleteByOrganizationId(event.getId());
    }

    @Override
    public DepartmentDTO add(DepartmentDTO department) {
        return mapper.departmentToDepartmentDTO(
                repository.save(mapper.departmentDTOToDepartment(department))
        );
    }

    @Override
    public List<DepartmentDTO> getDepartmentsByOrganizationId(Long id) {
        List<DepartmentDTO> dtoList = new ArrayList<>();
        repository.findByOrganizationId(id).forEach(item -> {
            dtoList.add(new DepartmentDTO(item.getId(), item.getOrganizationId(), item.getName()));
        });
        return dtoList;
    }

    @Override
    public List<DepartmentDTO> getDepartmentsByOrganizationIdWithEmployees(Long id) {
        List<DepartmentDTO> departments = getDepartmentsByOrganizationId(id);
        for (DepartmentDTO dep : departments) {
            dep.employees().addAll(employeeInternalAPI.getEmployeesByDepartmentId(dep.id()));
        }
        return departments;
    }
}
