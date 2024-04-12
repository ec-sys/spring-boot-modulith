package pl.piomin.modulith.module.gateway;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piomin.modulith.module.department.repository.DepartmentRepository;
import pl.piomin.modulith.module.employee.repository.EmployeeRepository;
import pl.piomin.modulith.module.organization.OrganizationDTO;
import pl.piomin.modulith.module.organization.model.Organization;
import pl.piomin.modulith.module.organization.repository.OrganizationRepository;

@Service
public class GatewayService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Transactional
    public long createOrganization(OrganizationDTO dto) {
        Organization organization = new Organization();
        organization.setName(dto.name());
        organization.setAddress(dto.address());
        var saved = organizationRepository.save(organization);
        return saved.getId();
    }

    public OrganizationDTO getOrganization(long orgId) {
        Organization organization = organizationRepository.findById(orgId).get();
        OrganizationDTO organizationDTO = new OrganizationDTO(organization.getId(), organization.getName(), organization.getAddress());
        return organizationDTO;
    }
}
