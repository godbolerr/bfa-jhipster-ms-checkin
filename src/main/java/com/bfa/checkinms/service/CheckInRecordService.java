package com.bfa.checkinms.service;

import com.bfa.checkinms.service.dto.CheckInRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CheckInRecord.
 */
public interface CheckInRecordService {

    /**
     * Save a checkInRecord.
     *
     * @param checkInRecordDTO the entity to save
     * @return the persisted entity
     */
    CheckInRecordDTO save(CheckInRecordDTO checkInRecordDTO);

    /**
     *  Get all the checkInRecords.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CheckInRecordDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" checkInRecord.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CheckInRecordDTO findOne(Long id);

    /**
     *  Delete the "id" checkInRecord.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
