package com.antoineleuf.conf.legacy.ws.infrastructure.md;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.antoineleuf.conf.legacy.ws.domain.md.Doctor;
import com.antoineleuf.conf.legacy.ws.domain.md.DoctorNotFoundException;
import com.antoineleuf.conf.legacy.ws.domain.md.DoctorRepository;

public class InMemoryDoctorRepository implements DoctorRepository {

  private Map<String, Doctor> doctors = new HashMap<>();

  @Override
  public List<Doctor> findAll() {
    return (List<Doctor>) doctors.values();
  }

  @Override
  public Doctor findById(String id) {
    return doctors.get(id);
  }

  @Override
  public void update(Doctor doctor) throws DoctorNotFoundException {
    String id = doctor.getId();
    if (!doctors.containsKey(id)) {
      throw new DoctorNotFoundException("Cannot find doctors");
    }
    doctors.replace(id, doctor);
  }

  @Override
  public void save(Doctor doctor) {
    doctors.put(doctor.getId(), doctor);
  }

  @Override
  public void remove(String id) {
    doctors.remove(id);
  }

}
