package pl.piomin.modulith.module.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.piomin.modulith.module.employee.EmployeeDTO;
import pl.piomin.modulith.module.employee.model.Employee;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    EmployeeDTO employeeToEmployeeDTO(Employee employee);
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
