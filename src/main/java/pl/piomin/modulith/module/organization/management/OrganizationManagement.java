package pl.piomin.modulith.module.organization.management;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.modulith.module.OrganizationAddEvent;
import pl.piomin.modulith.module.OrganizationRemoveEvent;
import pl.piomin.modulith.module.department.DepartmentDTO;
import pl.piomin.modulith.module.department.DepartmentInternalAPI;
import pl.piomin.modulith.module.employee.EmployeeDTO;
import pl.piomin.modulith.module.employee.EmployeeInternalAPI;
import pl.piomin.modulith.module.organization.OrganizationDTO;
import pl.piomin.modulith.module.organization.OrganizationExternalAPI;
import pl.piomin.modulith.module.organization.mapper.OrganizationMapper;
import pl.piomin.modulith.module.organization.model.Organization;
import pl.piomin.modulith.module.organization.repository.OrganizationRepository;

import java.util.List;

@Service
public class OrganizationManagement implements OrganizationExternalAPI {

    private final ApplicationEventPublisher events;
    private final OrganizationRepository repository;
    private final DepartmentInternalAPI departmentInternalAPI;
    private final EmployeeInternalAPI employeeInternalAPI;
    private final OrganizationMapper mapper;

    public OrganizationManagement(ApplicationEventPublisher events,
                                  OrganizationRepository repository,
                                  DepartmentInternalAPI departmentInternalAPI,
                                  EmployeeInternalAPI employeeInternalAPI,
                                  OrganizationMapper mapper) {
        this.events = events;
        this.repository = repository;
        this.departmentInternalAPI = departmentInternalAPI;
        this.employeeInternalAPI = employeeInternalAPI;
        this.mapper = mapper;
    }

    @Override
    public OrganizationDTO findByIdWithEmployees(Long id) {
        Organization obj = repository.findById(id).get();
        OrganizationDTO dto = new OrganizationDTO(obj.getId(), obj.getName(), obj.getAddress());
        List<EmployeeDTO> dtos = employeeInternalAPI.getEmployeesByOrganizationId(id);
        dto.employees().addAll(dtos);
        return dto;
    }

    @Override
    public OrganizationDTO findByIdWithDepartments(Long id) {
        Organization obj = repository.findById(id).get();
        OrganizationDTO dto = new OrganizationDTO(obj.getId(), obj.getName(), obj.getAddress());
        List<DepartmentDTO> dtos = departmentInternalAPI.getDepartmentsByOrganizationId(id);
        dto.departments().addAll(dtos);
        return dto;
    }

    @Override
    public OrganizationDTO findByIdWithDepartmentsAndEmployees(Long id) {
        Organization obj = repository.findById(id).get();
        OrganizationDTO dto = new OrganizationDTO(obj.getId(), obj.getName(), obj.getAddress());
        List<DepartmentDTO> dtos = departmentInternalAPI.getDepartmentsByOrganizationIdWithEmployees(id);
        dto.departments().addAll(dtos);
        return dto;
    }

    @Override
    @Transactional
    public OrganizationDTO add(OrganizationDTO organization) {
        OrganizationDTO dto = mapper.organizationToOrganizationDTO(
                repository.save(mapper.organizationDTOToOrganization(organization))
        );
        events.publishEvent(new OrganizationAddEvent(dto.id()));
        return dto;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
        events.publishEvent(new OrganizationRemoveEvent(id));
    }

}
