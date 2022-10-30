package com.antoineleuf.telephony.ws.api.calllog;

import java.util.List;

import com.antoineleuf.telephony.ws.api.calllog.dto.CallLogDto;
import com.antoineleuf.telephony.ws.domain.calllog.CallLogService;

public class CallLogResourceImpl implements CallLogResource {

  private CallLogService callLogService;

  public CallLogResourceImpl(CallLogService callLogService) {
    this.callLogService = callLogService;
  }

  @Override
  public List<CallLogDto> getCallLogs() {
    return callLogService.findAllCallLogs();
  }

  @Override
  public void deleteCallLog(String id) {
    callLogService.deleteCallLog(id);
  }
}
