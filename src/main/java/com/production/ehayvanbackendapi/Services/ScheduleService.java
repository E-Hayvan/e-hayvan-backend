package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.ScheduleDTO;
import com.production.ehayvanbackendapi.Entities.Schedule;
import com.production.ehayvanbackendapi.Mappers.ScheduleMapper;
import com.production.ehayvanbackendapi.Repositories.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public ScheduleDTO getScheduleById(Integer id) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        return schedule != null ? scheduleMapper.convertToDto(schedule) : null;
    }

    public ScheduleDTO deleteSchedule(Integer id) {
        // Find the target Schedule to delete.
        Optional<Schedule> targetSchedule = scheduleRepository.findById(id);

        // Delete Schedule and return DTO if it exists.
        if (targetSchedule.isPresent()) {
            scheduleRepository.delete(targetSchedule.get());
            return scheduleMapper.convertToDto(targetSchedule.get());
        }
        // Return null if the target entity does not exist.
        return null;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDtoList = new ArrayList<>();

        for (Schedule schedule : scheduleList) {
            scheduleDtoList.add(scheduleMapper.convertToDto(schedule));
        }

        return scheduleDtoList;
    }

//    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
//        Schedule schedule = scheduleMapper.convertToEntity(scheduleDTO);
//        Schedule savedSchedule = scheduleRepository.save(schedule);
//        return scheduleMapper.convertToDto(savedSchedule);
//    }

    // Other service methods for updating, deleting schedules, etc.
}

