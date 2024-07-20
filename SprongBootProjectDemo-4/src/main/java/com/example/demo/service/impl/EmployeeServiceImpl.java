package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFound;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {
   private EmployeeRepository employeeRepository;
   
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
	super();
	this.employeeRepository = employeeRepository;
}

	@Override
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {

		return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFound("Employee","id",id));
	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {
	
		Employee existingEmployee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFound("Employee","id",id));
		
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());

		existingEmployee.setEmail(employee.getEmail());
		employeeRepository.save(existingEmployee);
		return existingEmployee;

	
	
	}

	@Override
	public void deleteEmployee(long id) {
		employeeRepository.findById(id).orElseThrow(()->new ResourceNotFound("Employee","id",id));
		
		employeeRepository.deleteById(id);
	}

}
