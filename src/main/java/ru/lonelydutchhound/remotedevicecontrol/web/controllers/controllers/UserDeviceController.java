package ru.lonelydutchhound.remotedevicecontrol.web.controllers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lonelydutchhound.remotedevicecontrol.dto.DeviceActivityDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.DeviceActivityDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDeviceDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.logging.MethodWithMDC;
import ru.lonelydutchhound.remotedevicecontrol.services.device.WashingMachineDeviceService;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.AddWashingMachineDeviceRequest;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.StartProgramRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/washing-machine")
@Tag(name = "User remote control", description = "Remote control for devices from user side")
public class UserDeviceController {
    private final WashingMachineDeviceService washingMachineDeviceService;
    private final WashingMachineDeviceDTOMapper washingMachineDeviceDTOMapper;
    private final DeviceActivityDTOMapper deviceActivityDTOMapper;
    private final WashingMachineDTOMapper washingMachineDTOMapper;

    @Autowired
    public UserDeviceController(
            WashingMachineDeviceService washingMachineDeviceService,
            WashingMachineDeviceDTOMapper washingMachineDeviceDTOMapper,
            DeviceActivityDTOMapper deviceActivityDTOMapper,
            WashingMachineDTOMapper washingMachineDTOMapper
    ) {
        this.washingMachineDeviceService = washingMachineDeviceService;
        this.washingMachineDeviceDTOMapper = washingMachineDeviceDTOMapper;
        this.deviceActivityDTOMapper = deviceActivityDTOMapper;
        this.washingMachineDTOMapper = washingMachineDTOMapper;
    }

    @MethodWithMDC
    @Operation(
            summary = "Get all washing machine models with full info",
            description = "Full information about washing machines with program list",
            tags = {"machine"}
    )
    @GetMapping("/machines")
    public ResponseEntity<List<WashingMachineDTO>> getAllMachines() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingMachineDeviceService.getAllMachines().stream().map(washingMachineDTOMapper::mapEntityToDto).collect(Collectors.toList()));
    }

    @MethodWithMDC
    @Operation(
            summary = "Get information about devices added to remote control",
            description = "Full information about devices with program list and current power status",
            tags = {"devices"}
    )
    @GetMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WashingMachineDeviceDTO>> getAllDevices() {
        var washingMachineDeviceDTOList = washingMachineDeviceService.getAllActiveDevices()
                .stream().map(washingMachineDeviceDTOMapper::mapEntityToDto).collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingMachineDeviceDTOList);
    }

    @MethodWithMDC
    @Operation(
            summary = "Add new washing machine device to remote control",
            description = "Washing machine model is searched by id, then a new device is created in remote control list",
            tags = {"device"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created",
                    content = @Content(schema = @Schema(implementation = WashingMachineDeviceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent or SQL constraints violated"),
            @ApiResponse(responseCode = "404", description = "Washing machine model wasn't found")
    })
    @PostMapping(
            value = "/devices",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WashingMachineDeviceDTO> addWashingMachineDevice(
            @Parameter(description = "Washing machine model to add. Id can't be null or empty.", required = true, schema = @Schema(implementation = AddWashingMachineDeviceRequest.class))
            @RequestBody AddWashingMachineDeviceRequest request) {
        var device = washingMachineDeviceService.createDevice(request.getMachineId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(washingMachineDeviceDTOMapper.mapEntityToDto(device));
    }

    @MethodWithMDC
    @Operation(
            summary = "Get full information about concrete washing machine device by its id",
            description = "Full information about devices with program list and current power status",
            tags = {"device"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Full device info",
                    content = @Content(schema = @Schema(implementation = WashingMachineDeviceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent"),
            @ApiResponse(responseCode = "404", description = "Washing machine device wasn't found by provided id")
    })
    @GetMapping(value = "/devices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WashingMachineDeviceDTO> getWashingMachineById(@PathVariable String id) {
        var device = washingMachineDeviceService.getDeviceById(UUID.fromString(id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingMachineDeviceDTOMapper.mapEntityToDto(device));
    }

    @MethodWithMDC
    @Operation(
            summary = "Delete concrete washing machine device from remote control by its id",
            description = "Washing machine device is searched by id, then a new device is deleted, endpoint returns info about deleted device",
            tags = {"device"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Washing machine device deleted from remote control list"),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent"),
            @ApiResponse(responseCode = "404", description = "Washing machine device wasn't found by provided id")
    })
    @DeleteMapping(value = "/devices/{id}")
    public void deleteDevice(@PathVariable String id) {
        washingMachineDeviceService.deleteDeviceById(UUID.fromString(id));
    }

    @MethodWithMDC
    @Operation(
            summary = "Start program on device",
            description = "Washing machine device is searched by its id, program is searched by its id in device program set",
            tags = {"activity"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Program starting on device, record created",
                    content = @Content(schema = @Schema(implementation = DeviceActivityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameters are not valid or absent"),
            @ApiResponse(responseCode = "404", description = "Washing machine device or program weren't found by provided id"),
            @ApiResponse(responseCode = "409", description = "Device is in incorrect power or program state")
    })
    @PostMapping(
            value = "/programs/start",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DeviceActivityDTO> startProgram(
            @Parameter(description = "DeviceId of the device on that we wish to run a program with programId, id can't be null or empty.", required = true, schema = @Schema(implementation = StartProgramRequest.class))
            @RequestBody @Valid StartProgramRequest request
    ) {
        var activity = washingMachineDeviceService.startNewDeviceProgram(request.getDeviceId(), request.getProgramId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(deviceActivityDTOMapper.mapEntityToDto(activity));
    }
}
