package com.Pos10Max.POS10APIMAX.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.Pos10Max.POS10APIMAX.DTO.SettingDto;
import com.Pos10Max.POS10APIMAX.Service.SettingsService;
import com.Pos10Max.POS10APIMAX.Shared.Utils;
import com.Pos10Max.POS10APIMAX.UiRequest.SettingsRequest;
import com.Pos10Max.POS10APIMAX.UiResponse.OperationalStatusModel;
import com.Pos10Max.POS10APIMAX.UiResponse.RequestOperationName;
import com.Pos10Max.POS10APIMAX.UiResponse.RequestOperationStatus;
import com.Pos10Max.POS10APIMAX.UiResponse.SettingsResponse;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    Utils utils;

    @Autowired
    SettingsService settingsService;

    @PostMapping(produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public SettingsResponse saveSettings(@RequestBody SettingsRequest settingsRequest)
    {
        SettingDto settingsDto=settingsService.saveSettings(utils.getMapper().map(settingsRequest,SettingDto.class));
        if(settingsDto==null)
            throw new RuntimeException("Failed To Save");
        SettingsResponse returnValue=utils.getMapper().map(settingsDto,SettingsResponse.class);
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SettingsResponse getSettings()
    {
        SettingDto dto=settingsService.getSettings(1);
        return utils.getMapper().map(dto,SettingsResponse.class);
    }

    @PutMapping(produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public SettingsResponse updateSettings(@RequestBody SettingsRequest settingsRequest)
    {
        SettingDto settingsDto=settingsService.saveSettings(utils.getMapper().map(settingsRequest,SettingDto.class));
        if(settingsDto==null)
            throw new RuntimeException("Failed To Update");
        SettingsResponse returnValue=utils.getMapper().map(settingsDto,SettingsResponse.class);
        return returnValue;
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public OperationalStatusModel deleteSettings(@PathVariable int id)
    {
        OperationalStatusModel returnValue=new OperationalStatusModel();
        returnValue.setOperationType(RequestOperationName.DELETE.toString());
        returnValue.setOperationResult(RequestOperationStatus.FAILED.toString());

        if(settingsService.deleteSetting(id))
        {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.toString());
        }

        return returnValue;
    }


}
