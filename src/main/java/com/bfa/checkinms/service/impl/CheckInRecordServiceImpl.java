package com.bfa.checkinms.service.impl;

import com.bfa.checkinms.service.CheckInRecordService;
import com.bfa.checkinms.domain.CheckInRecord;
import com.bfa.checkinms.repository.CheckInRecordRepository;
import com.bfa.checkinms.service.dto.CheckInRecordDTO;
import com.bfa.checkinms.service.mapper.CheckInRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CheckInRecord.
 */
@Service
@Transactional
public class CheckInRecordServiceImpl implements CheckInRecordService{

    private final Logger log = LoggerFactory.getLogger(CheckInRecordServiceImpl.class);
    
    @Inject
    private CheckInRecordRepository checkInRecordRepository;

    @Inject
    private CheckInRecordMapper checkInRecordMapper;

    /**
     * Save a checkInRecord.
     *
     * @param checkInRecordDTO the entity to save
     * @return the persisted entity
     */
    public CheckInRecordDTO save(CheckInRecordDTO checkInRecordDTO) {
        log.debug("Request to save CheckInRecord : {}", checkInRecordDTO);
        CheckInRecord checkInRecord = checkInRecordMapper.checkInRecordDTOToCheckInRecord(checkInRecordDTO);
        checkInRecord = checkInRecordRepository.save(checkInRecord);
        CheckInRecordDTO result = checkInRecordMapper.checkInRecordToCheckInRecordDTO(checkInRecord);
        return result;
    }

    /**
     *  Get all the checkInRecords.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CheckInRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckInRecords");
        Page<CheckInRecord> result = checkInRecordRepository.findAll(pageable);
        return result.map(checkInRecord -> checkInRecordMapper.checkInRecordToCheckInRecordDTO(checkInRecord));
    }

    /**
     *  Get one checkInRecord by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CheckInRecordDTO findOne(Long id) {
        log.debug("Request to get CheckInRecord : {}", id);
        CheckInRecord checkInRecord = checkInRecordRepository.findOne(id);
        CheckInRecordDTO checkInRecordDTO = checkInRecordMapper.checkInRecordToCheckInRecordDTO(checkInRecord);
        return checkInRecordDTO;
    }

    /**
     *  Delete the  checkInRecord by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CheckInRecord : {}", id);
        checkInRecordRepository.delete(id);
    }
}
