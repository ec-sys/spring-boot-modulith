package pl.piomin.modulith.module.gateway;

import org.springframework.web.bind.annotation.*;
import pl.piomin.modulith.module.department.DepartmentDTO;
import pl.piomin.modulith.module.department.DepartmentExternalAPI;
import pl.piomin.modulith.module.employee.EmployeeDTO;
import pl.piomin.modulith.module.employee.EmployeeExternalAPI;
import pl.piomin.modulith.module.organization.OrganizationDTO;
import pl.piomin.modulith.module.organization.OrganizationExternalAPI;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GatewayManagement {

    private final DepartmentExternalAPI departmentExternalAPI;
    private final EmployeeExternalAPI employeeExternalAPI;
    private final OrganizationExternalAPI organizationExternalAPI;

    public GatewayManagement(DepartmentExternalAPI departmentExternalAPI,
                             EmployeeExternalAPI employeeExternalAPI,
                             OrganizationExternalAPI organizationExternalAPI) {
        this.departmentExternalAPI = departmentExternalAPI;
        this.employeeExternalAPI = employeeExternalAPI;
        this.organizationExternalAPI = organizationExternalAPI;
    }


    @GetMapping("/organizations/{id}/with-departments")
    public OrganizationDTO apiOrganizationWithDepartments(@PathVariable("id") Long id) {
        return organizationExternalAPI.findByIdWithDepartments(id);
    }

    @GetMapping("/organizations/{id}/with-departments-and-employees")
    public OrganizationDTO apiOrganizationWithDepartmentsAndEmployees(@PathVariable("id") Long id) {
        return organizationExternalAPI.findByIdWithDepartmentsAndEmployees(id);
    }

    @PostMapping("/organizations")
    public OrganizationDTO apiAddOrganization(@RequestBody OrganizationDTO o) {
        return organizationExternalAPI.add(o);
    }

    @PostMapping("/employees")
    public EmployeeDTO apiAddEmployee(@RequestBody EmployeeDTO employee) {
        return employeeExternalAPI.add(employee);
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> apiGetAllEmployees() {
        return employeeExternalAPI.getAll();
    }

    @GetMapping("/departments/{id}/with-employees")
    public DepartmentDTO apiDepartmentWithEmployees(@PathVariable("id") Long id) {
        return departmentExternalAPI.getDepartmentByIdWithEmployees(id);
    }

    @PostMapping("/departments")
    public DepartmentDTO apiAddDepartment(@RequestBody DepartmentDTO department) {
        return departmentExternalAPI.add(department);
    }
}
