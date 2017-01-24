package com.bfa.checkinms.repository;

import com.bfa.checkinms.domain.CheckInRecord;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CheckInRecord entity.
 */
@SuppressWarnings("unused")
public interface CheckInRecordRepository extends JpaRepository<CheckInRecord,Long> {

}
