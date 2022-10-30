package com.antoineleuf.telephony.ws.infrastructure.calllog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.antoineleuf.telephony.ws.domain.calllog.CallLog;
import com.antoineleuf.telephony.ws.domain.calllog.CallLogRepository;

public class CallLogRepositoryInMemory implements CallLogRepository {

  private Map<String, CallLog> callLogs = new HashMap<>();

  @Override
  public List<CallLog> findAll() {
    return callLogs.values().stream().toList();
  }

  @Override
  public void save(CallLog callLog) {
    callLogs.put(callLog.id(), callLog);
  }

  @Override
  public void remove(String id) {
    callLogs.remove(id);
  }
}
