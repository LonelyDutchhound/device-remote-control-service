package ru.lonelydutchhound.remotedevicecontrol.services.device;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.IncorrectDeviceStateException;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.NotFoundException;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceactivity.WashingMachineDeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.smartappliance.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.models.types.PowerStatus;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceActivityRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

@SpringJUnitConfig
@ContextConfiguration(classes = {WashingMachineDeviceService.class})
@DisplayName("UserWashingMachineDeviceService test")
public class WashingMachineDeviceServiceTest {
  private final UUID id = UUID.randomUUID();
  private final WashingMachineDeviceService washingMachineDeviceService;
  @Mock
  WashingMachineDevice washingMachineDevice;
  @Mock
  WashingMachine washingMachine;
  @Mock
  WashingProgram washingProgram;
  @MockBean
  private DeviceRepository deviceRepository;
  @MockBean
  private DeviceActivityRepository deviceActivityRepository;
  @MockBean
  private WashingProgramRepository washingProgramRepository;
  @MockBean
  private WashingMachineRepository washingMachineRepository;

  @Autowired
  public WashingMachineDeviceServiceTest(WashingMachineDeviceService washingMachineDeviceService) {
    this.washingMachineDeviceService = washingMachineDeviceService;
  }

  @Test
  @DisplayName("All machines obtaining succeed")
  void getAllMachines_Succeed() {
    Mockito.when(washingMachineRepository.findAll())
        .thenReturn(List.of(washingMachine));

    var result = washingMachineDeviceService.getAllMachines();
    assertNotNull(result);
    assertEquals(result.size(), 1);
    assertTrue(result.contains(washingMachine));
  }

  @Test
  @DisplayName("All devices obtaining succeed")
  void getAllActiveDevices_Succeed() {
    Mockito.when(deviceRepository.findAllByDeletedAtIsNull())
        .thenReturn(new ArrayList<>());

    var result = washingMachineDeviceService.getAllActiveDevices();
    assertNotNull(result);
  }

  @Test
  @DisplayName("Device by id obtaining succeeds")
  void getDeviceById_Succeed() {
    Mockito.when(deviceRepository.findById(id))
        .thenReturn(Optional.of(washingMachineDevice));

    var result = washingMachineDeviceService.getDeviceById(id);
    assertNotNull(result);
    assertEquals(result, washingMachineDevice);
  }

  @Test
  @DisplayName("Get device by id fails")
  void getDeviceById_FailsAndThrow() {
    Exception exception = assertThrows(NotFoundException.class, () -> {
      var id = UUID.randomUUID();
      Mockito.when(deviceRepository.findById(id))
          .thenReturn(Optional.empty());

      washingMachineDeviceService.getDeviceById(id);
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

    var result = washingMachineDeviceService.createDevice(id);
    assertEquals(result, washingMachineDevice);
  }

  @Test
  @DisplayName("Device creation fails")
  void createDevice_FailsAndThrow() {
    Exception exception = assertThrows(NotFoundException.class, () -> {
      Mockito.when(washingMachineRepository.findById(id))
          .thenReturn(Optional.empty());

      washingMachineDeviceService.createDevice(id);
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

    Mockito.when(deviceActivityRepository.findOneByWashingMachineDeviceIdAndProgramStatusNot(id,
            ProgramStatus.FINISHED))
        .thenReturn(Optional.empty());

    washingMachineDeviceService.deleteDeviceById(id);
    Mockito.verify(deviceRepository, times(1)).delete(washingMachineDevice);
  }

  @Test
  @DisplayName("Deletion of the device by id fails if no device was found")
  void deleteDeviceById_FailsNoDeviceFound() {
    Exception exception = assertThrows(NotFoundException.class, () -> {
      Mockito.when(deviceRepository.findById(any()))
          .thenReturn(Optional.empty());

      washingMachineDeviceService.deleteDeviceById(id);
    });

    String expectedMessage = "No device with id";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @DisplayName("Deletion of the device by id fails if device is running a program")
  void deleteDeviceById_FailsDeviceRunningProgram() {
    Exception exception = assertThrows(IncorrectDeviceStateException.class, () -> {
      Mockito.when(deviceRepository.findById(id))
          .thenReturn(Optional.ofNullable(washingMachineDevice));

      var activity = new WashingMachineDeviceActivity.DeviceActivityBuilder().setProgramStatus(
          ProgramStatus.RUNNING).build();
      Mockito.when(deviceActivityRepository
              .findOneByWashingMachineDeviceIdAndProgramStatusNot(any(), any()))
          .thenReturn(Optional.of(activity));

      washingMachineDeviceService.deleteDeviceById(id);
    });

    String expectedMessage = "is running a program right now";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @DisplayName("Deletion of the device by id fails if device program was stopped with error")
  void deleteDeviceById_FailsDeviceErrorProgram() {
    Exception exception = assertThrows(IncorrectDeviceStateException.class, () -> {
      Mockito.when(deviceRepository.findById(id))
          .thenReturn(Optional.ofNullable(washingMachineDevice));

      var activity = new WashingMachineDeviceActivity.DeviceActivityBuilder().setProgramStatus(
          ProgramStatus.ERROR).build();
      Mockito.when(deviceActivityRepository
              .findOneByWashingMachineDeviceIdAndProgramStatusNot(any(), any()))
          .thenReturn(Optional.of(activity));

      washingMachineDeviceService.deleteDeviceById(id);
    });

    String expectedMessage = "has error program status";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @DisplayName("Start of new program on the device succeed")
  void startNewDeviceProgram_Succeed() {
    var device = new WashingMachineDevice(washingMachine);
    Mockito.when(deviceRepository.findById(id))
        .thenReturn(Optional.of(device));
    Mockito.when(washingProgramRepository.findById(any()))
        .thenReturn(Optional.of(washingProgram));
    Mockito.when(washingProgram.getWashingMachineSet())
        .thenReturn(Set.of(washingMachine));
    Mockito.when(
            deviceActivityRepository.findOneByWashingMachineDeviceIdAndProgramStatusNot(any(), any()))
        .thenReturn(Optional.empty());

    washingMachineDeviceService.startNewDeviceProgram(id, UUID.randomUUID());
    Mockito.verify(deviceActivityRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("Start of new program on the device fails due to program is not implemented on device")
  void deleteDeviceById_FailsDeviceNotSupportedProgram() {
    Exception exception = assertThrows(NotFoundException.class, () -> {
      var device = new WashingMachineDevice(washingMachine);
      Mockito.when(deviceRepository.findById(id))
          .thenReturn(Optional.of(device));
      Mockito.when(washingProgramRepository.findById(any()))
          .thenReturn(Optional.of(washingProgram));
      Mockito.when(washingProgram.getWashingMachineSet())
          .thenReturn(new HashSet<>());

      washingMachineDeviceService.startNewDeviceProgram(id, UUID.randomUUID());
    });

    String expectedMessage = "No program with id";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @DisplayName("Start of new program on the device fails due switched off device")
  void deleteDeviceById_FailsDeviceSwitchedOff() {
    Exception exception = assertThrows(IncorrectDeviceStateException.class, () -> {
      var device = new WashingMachineDevice(washingMachine);
      device.updatePowerStatus(PowerStatus.OFF);
      Mockito.when(deviceRepository.findById(id))
          .thenReturn(Optional.of(device));

      washingMachineDeviceService.startNewDeviceProgram(id, UUID.randomUUID());
    });

    String expectedMessage = "is switched off";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @DisplayName("Start of new program on the device fails due to program absence(incorrect id)")
  void deleteDeviceById_FailsNoProgramFound() {
    Exception exception = assertThrows(NotFoundException.class, () -> {
      var device = new WashingMachineDevice(washingMachine);
      Mockito.when(deviceRepository.findById(id))
          .thenReturn(Optional.of(device));
      Mockito.when(washingProgramRepository.findById(any()))
          .thenReturn(Optional.empty());

      washingMachineDeviceService.startNewDeviceProgram(id, UUID.randomUUID());
    });

    String expectedMessage = "No program with id";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}
