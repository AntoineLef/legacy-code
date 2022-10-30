package com.antoineleuf.telephony.ws.domain.calllog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antoineleuf.telephony.ws.api.calllog.dto.CallLogDto;

import java.util.List;
import java.util.stream.Collectors;

public class CallLogService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CallLogService.class);

  private CallLogRepository callLogRepository;
  private CallLogAssembler callLogAssembler;

  public CallLogService(CallLogRepository callLogRepository, CallLogAssembler callLogAssembler) {
    this.callLogRepository = callLogRepository;
    this.callLogAssembler = callLogAssembler;
  }

  public List<CallLogDto> findAllCallLogs() {
    LOGGER.info("Get all call logs");
    List<CallLog> callLogs = callLogRepository.findAll();
    return callLogs.stream().map(callLogAssembler::create).collect(Collectors.toList());
  }

  public void deleteCallLog(String id) {
    LOGGER.info("Delete call log with id {}", id);
    callLogRepository.remove(id);
  }
}
