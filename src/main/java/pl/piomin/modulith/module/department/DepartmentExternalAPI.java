package pl.piomin.modulith.module.department;

public interface DepartmentExternalAPI {

    DepartmentDTO getDepartmentByIdWithEmployees(Long id);

    DepartmentDTO add(DepartmentDTO department);
}
