package ru.lonelydutchhound.remotedevicecontrol.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.NotFoundException;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceActivityRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringJUnitConfig
@ContextConfiguration(classes = {UserWashingMachineDeviceService.class})
@DisplayName("UserWashingMachineDeviceService test")
public class UserWashingMachineDeviceServiceTest {
    private final UUID id = UUID.randomUUID();
    private final UserWashingMachineDeviceService userWashingMachineDeviceService;

    @MockBean
    private DeviceRepository deviceRepository;
    @MockBean
    private DeviceActivityRepository deviceActivityRepository;
    @MockBean
    private WashingProgramRepository washingProgramRepository;
    @MockBean
    private WashingMachineRepository washingMachineRepository;

    @Mock
    WashingMachineDevice washingMachineDevice;
    @Mock
    WashingMachine washingMachine;

    @Autowired
    public UserWashingMachineDeviceServiceTest(UserWashingMachineDeviceService userWashingMachineDeviceService) {
        this.userWashingMachineDeviceService = userWashingMachineDeviceService;
    }

    @Test
    @DisplayName("All devices obtaining succeed")
    void getAllActiveDevicesSucceed() {
        Mockito.when(deviceRepository.findAllByDeletedAtIsNull())
                .thenReturn(new ArrayList<>());

        var result = userWashingMachineDeviceService.getAllActiveDevices();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Device by id obtaining succeeds")
    void getDeviceById_Succeed() {
        Mockito.when(deviceRepository.findById(id))
                .thenReturn(Optional.of(new WashingMachineDevice()));

        var result = userWashingMachineDeviceService.getDeviceById(id);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Get device by id fails")
    void getDeviceById_FailsAndThrow() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            var id = UUID.randomUUID();
            Mockito.when(deviceRepository.findById(id))
                    .thenReturn(Optional.empty());

            userWashingMachineDeviceService.getDeviceById(id);
        });

        String expectedMessage = "No device with id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    @DisplayName("Device creation succeed")
    void createDevice_Succeed() {
        Mockito.when(washingMachineRepository.findById(any()))
                .thenReturn(Optional.of(washingMachine));

        Mockito.when(deviceRepository.save(any()))
                .thenReturn(washingMachineDevice);

        var result = userWashingMachineDeviceService.createDevice(id);
        assertEquals(result, washingMachineDevice);
    }

    @Test
    @DisplayName("Device creation fails")
    void createDevice_FailsAndThrow() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            Mockito.when(washingMachineRepository.findById(id))
                    .thenReturn(Optional.empty());

            userWashingMachineDeviceService.createDevice(id);
        });

        String expectedMessage = "No device with id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Deletion of the device by id succeed")
    void deleteDeviceById_Succeed() {
        Mockito.when(deviceRepository.findById(id))
                .thenReturn(Optional.ofNullable(washingMachineDevice));

        Mockito.when(deviceActivityRepository.findOneByWashingMachineDeviceIdAndProgramStatusNot(id, ProgramStatus.FINISHED))
                .thenReturn(Optional.empty());

        userWashingMachineDeviceService.deleteDeviceById(id);
        Mockito.verify(deviceRepository, times(1)).delete(washingMachineDevice);
    }
}
