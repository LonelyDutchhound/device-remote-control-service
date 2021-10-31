package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.DeviceActivityDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDeviceDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.services.UserWashingMachineDeviceService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserDeviceController.class)
public class UserDeviceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserWashingMachineDeviceService userWashingMachineDeviceService;
    @MockBean
    private WashingMachineDeviceDTOMapper washingMachineDeviceDTOMapper;
    @MockBean
    private DeviceActivityDTOMapper deviceActivityDTOMapper;

    @Mock
    WashingMachineDevice washingMachineDevice;

    @Test
    public void  getAllDevicesAPI() throws Exception {
        List<WashingMachineDeviceDTO> allActiveDevicesList = new ArrayList<>();
        allActiveDevicesList.add(washingMachineDeviceDTOMapper.mapEntityToDto(washingMachineDevice));
        doReturn(allActiveDevicesList).when(userWashingMachineDeviceService).getAllActiveDevices();

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/washing-machine/devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }
}
