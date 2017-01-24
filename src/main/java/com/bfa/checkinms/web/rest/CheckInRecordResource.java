package com.bfa.checkinms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bfa.checkinms.service.CheckInRecordService;
import com.bfa.checkinms.web.rest.util.HeaderUtil;
import com.bfa.checkinms.web.rest.util.PaginationUtil;
import com.bfa.checkinms.service.dto.CheckInRecordDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CheckInRecord.
 */
@RestController
@RequestMapping("/api")
public class CheckInRecordResource {

    private final Logger log = LoggerFactory.getLogger(CheckInRecordResource.class);
        
    @Inject
    private CheckInRecordService checkInRecordService;

    /**
     * POST  /check-in-records : Create a new checkInRecord.
     *
     * @param checkInRecordDTO the checkInRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new checkInRecordDTO, or with status 400 (Bad Request) if the checkInRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/check-in-records")
    @Timed
    public ResponseEntity<CheckInRecordDTO> createCheckInRecord(@RequestBody CheckInRecordDTO checkInRecordDTO) throws URISyntaxException {
        log.debug("REST request to save CheckInRecord : {}", checkInRecordDTO);
        if (checkInRecordDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("checkInRecord", "idexists", "A new checkInRecord cannot already have an ID")).body(null);
        }
        CheckInRecordDTO result = checkInRecordService.save(checkInRecordDTO);
        return ResponseEntity.created(new URI("/api/check-in-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("checkInRecord", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /check-in-records : Updates an existing checkInRecord.
     *
     * @param checkInRecordDTO the checkInRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated checkInRecordDTO,
     * or with status 400 (Bad Request) if the checkInRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the checkInRecordDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/check-in-records")
    @Timed
    public ResponseEntity<CheckInRecordDTO> updateCheckInRecord(@RequestBody CheckInRecordDTO checkInRecordDTO) throws URISyntaxException {
        log.debug("REST request to update CheckInRecord : {}", checkInRecordDTO);
        if (checkInRecordDTO.getId() == null) {
            return createCheckInRecord(checkInRecordDTO);
        }
        CheckInRecordDTO result = checkInRecordService.save(checkInRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("checkInRecord", checkInRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /check-in-records : get all the checkInRecords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of checkInRecords in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/check-in-records")
    @Timed
    public ResponseEntity<List<CheckInRecordDTO>> getAllCheckInRecords(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CheckInRecords");
        Page<CheckInRecordDTO> page = checkInRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/check-in-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /check-in-records/:id : get the "id" checkInRecord.
     *
     * @param id the id of the checkInRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the checkInRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/check-in-records/{id}")
    @Timed
    public ResponseEntity<CheckInRecordDTO> getCheckInRecord(@PathVariable Long id) {
        log.debug("REST request to get CheckInRecord : {}", id);
        CheckInRecordDTO checkInRecordDTO = checkInRecordService.findOne(id);
        return Optional.ofNullable(checkInRecordDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /check-in-records/:id : delete the "id" checkInRecord.
     *
     * @param id the id of the checkInRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/check-in-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteCheckInRecord(@PathVariable Long id) {
        log.debug("REST request to delete CheckInRecord : {}", id);
        checkInRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("checkInRecord", id.toString())).build();
    }

}
