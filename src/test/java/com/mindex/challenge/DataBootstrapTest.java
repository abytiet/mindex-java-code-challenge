package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void testEmployee() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
    }

    @Test
    public void testReportingStructure(){
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        ReportingStructure rs = new ReportingStructure(employee, 4);
        assertNotNull(rs);
        assertEquals(employee, rs.getEmployee());
        assertEquals(4, rs.getNumberOfReports());
    }

    @Test
    public void testCompensation(){
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        Compensation c = new Compensation(employee, 40000.00, "January 8th, 2021");
        assertNotNull(c);
        assertEquals(employee, c.getEmployee());
        assertEquals(40000.00, c.getSalary(), .01);
        assertEquals("January 8th, 2021", c.getEffectiveDate());
    }
}