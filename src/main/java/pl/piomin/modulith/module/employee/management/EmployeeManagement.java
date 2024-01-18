package pl.piomin.modulith.module.employee.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.modulith.module.OrganizationRemoveEvent;
import pl.piomin.modulith.module.employee.EmployeeDTO;
import pl.piomin.modulith.module.employee.EmployeeExternalAPI;
import pl.piomin.modulith.module.employee.EmployeeInternalAPI;
import pl.piomin.modulith.module.employee.mapper.EmployeeMapper;
import pl.piomin.modulith.module.employee.model.Employee;
import pl.piomin.modulith.module.employee.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeManagement implements EmployeeInternalAPI, EmployeeExternalAPI {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeManagement.class);
    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    public EmployeeManagement(EmployeeRepository repository,
                              EmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByOrganizationId(Long id) {
        return repository.findByOrganizationId(id);
    }

    @Override
    @Transactional
    public EmployeeDTO add(EmployeeDTO employee) {
        Employee emp = mapper.employeeDTOToEmployee(employee);
        return mapper.employeeToEmployeeDTO(repository.save(emp));
    }

    @Override
    public List<EmployeeDTO> getAll() {
        List<EmployeeDTO> allEmployees = new ArrayList<>();
        repository.findAll().forEach(item -> {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    item.getId(), item.getOrganizationId(), item.getDepartmentId(), item.getName(), item.getAge(), item.getPosition()
            );
        });
        return allEmployees;
    }

    @ApplicationModuleListener
    void onRemovedOrganizationEvent(OrganizationRemoveEvent event) {
        LOG.info("onRemovedOrganizationEvent(orgId={})", event.getId());
        repository.deleteByOrganizationId(event.getId());
    }

}
