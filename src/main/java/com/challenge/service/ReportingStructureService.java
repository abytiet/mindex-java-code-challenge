package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

public interface ReportingStructureService {
    ReportingStructure create(ReportingStructure rs);
    ReportingStructure read(String id);
    ReportingStructure update(ReportingStructure rs);
}
