package com.Pos10Max.POS10APIMAX.Service;


import com.Pos10Max.POS10APIMAX.DTO.SettingDto;

public interface SettingsService {
    SettingDto saveSettings(SettingDto settingsDto);
    boolean deleteSetting(int id);
    SettingDto getSettings(int id);

}
