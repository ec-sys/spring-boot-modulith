package pl.piomin.modulith.module.department.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.piomin.modulith.module.department.DepartmentDTO;
import pl.piomin.modulith.module.department.model.Department;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    DepartmentDTO departmentToDepartmentDTO(Department department);

    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);
}
