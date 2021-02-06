package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Creating reportingstructure with employee id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        int reports = numReportsCalculator(employee);
        LOG.debug("Creating reportingstructure with employee id reports amount[{}]", reports);

        return new ReportingStructure(employee, reports);
    }

    private int numReportsCalculator(Employee employee){
        LOG.debug("calculating reports for employee[{}]", employee.getFirstName());
        if(employee == null){
            return 0;
        }
        if(employee.getDirectReports() == null){
            return 0;
        }
        int reports = 0;
        for(Employee reporter : employee.getDirectReports()){
            reporter = employeeRepository.findByEmployeeId(reporter.getEmployeeId());
            LOG.debug("calculating reporter[{}]", reporter.getFirstName());
            reports += 1;
            reports += numReportsCalculator(reporter);
        }
        return reports;
    }

}
