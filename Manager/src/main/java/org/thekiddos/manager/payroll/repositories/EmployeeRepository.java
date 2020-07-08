package org.thekiddos.manager.payroll.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thekiddos.manager.payroll.models.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
