package com.mindex.challenge.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String rsUrl;
    private String employeeUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        rsUrl = "http://localhost:" + port + "/reportingstructure/{id}";
    }

    @Test
    public void testRead() {
        //build test employee
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        List<Employee> l = new ArrayList<Employee>();

        Employee reporter1 = new Employee();
        reporter1.setFirstName("Jim");
        reporter1.setLastName("Doe");
        reporter1.setDepartment("Engineering");
        reporter1.setPosition("Developer");
        reporter1.setDirectReports(null);

        Employee reporter2 = new Employee();
        reporter2.setFirstName("Jack");
        reporter2.setLastName("Doe");
        reporter2.setDepartment("Engineering");
        reporter2.setPosition("Developer");
        reporter2.setDirectReports(null);

        //create reporters to add them to the repository
        Employee createdReporter1 = restTemplate.postForEntity(employeeUrl, reporter1, Employee.class).getBody();
        Employee createdReporter2 = restTemplate.postForEntity(employeeUrl, reporter2, Employee.class).getBody();

        l.add(createdReporter1);
        l.add(createdReporter2);
        testEmployee.setDirectReports(l);

        //create employee from test employee
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        //build reportingstructure given test employee
        ReportingStructure testRS = new ReportingStructure(testEmployee, 2);

        // create reportingstructure from createdemployee
        String createdId = createdEmployee.getEmployeeId();
        ReportingStructure readRS = restTemplate.getForEntity(rsUrl, ReportingStructure.class, createdId).getBody();

        //compare
        assertNotNull(readRS);
        assertReportingStructureEquivalence(testRS, readRS);

    }

    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getEmployee(), actual.getEmployee());
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
    }

}
