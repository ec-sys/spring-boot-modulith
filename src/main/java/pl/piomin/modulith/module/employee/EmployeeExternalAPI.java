package pl.piomin.modulith.module.employee;

import java.util.List;

public interface EmployeeExternalAPI {

    EmployeeDTO add(EmployeeDTO employee);

    List<EmployeeDTO> getAll();
}
