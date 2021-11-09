package com.Pos10Max.POS10APIMAX.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Pos10Max.POS10APIMAX.DTO.SettingDto;
import com.Pos10Max.POS10APIMAX.Entity.SettingsEntity;
import com.Pos10Max.POS10APIMAX.Repository.SettingsRepository;
import com.Pos10Max.POS10APIMAX.Service.SettingsService;
import com.Pos10Max.POS10APIMAX.Shared.Utils;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    Utils utils;

    @Override
    public SettingDto saveSettings(SettingDto settingsDto) {


        SettingsEntity savableEntity=utils.getMapper().map(settingsDto, SettingsEntity.class);
        savableEntity.setId(1);

        SettingsEntity savedSettings=settingsRepository.save(savableEntity);

        if(savedSettings==null)
            throw new RuntimeException("Failed to Save Plese Chack all the details");

        return utils.getMapper().map(savedSettings,SettingDto.class);
    }

    @Override
    public boolean deleteSetting(int id) {
        return false;
    }

    @Override
    public SettingDto getSettings(int id) {

        SettingsEntity getSettings=settingsRepository.getById(1);

        if(getSettings==null)
            throw new RuntimeException("Failed get Settings Details");

        return utils.getMapper().map(getSettings,SettingDto.class);
    }



    public boolean deleteSetting(SettingDto SettingsDto) {
     SettingsEntity entity=settingsRepository.getById(1);
     if(entity==null)
         throw new RuntimeException("Settings Data unavailable");

     settingsRepository.delete(entity);
        if(settingsRepository.getById(1)==null)
        {
            return true;
        }

        return false;
    }
}
