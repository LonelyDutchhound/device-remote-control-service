package ru.lonelydutchhound.remotedevicecontrol.web.controllers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingProgramDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingProgramDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.logging.MethodWithMDC;
import ru.lonelydutchhound.remotedevicecontrol.services.AdminWashingMachineService;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.AddWashingMachineRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/washing-machine")
@Tag(name = "Admin control panel", description = "Customise user devices")
public class AdminDeviceController {
    private final AdminWashingMachineService adminWashingMachineService;
    private final WashingProgramDTOMapper washingProgramDTOMapper;
    private final WashingMachineDTOMapper washingMachineDTOMapper;

    @Autowired
    public AdminDeviceController(
            AdminWashingMachineService adminWashingMachineService,
            WashingProgramDTOMapper washingProgramDTOMapper,
        WashingMachineDTOMapper washingMachineDTOMapper
    ) {
        this.adminWashingMachineService = adminWashingMachineService;
        this.washingProgramDTOMapper = washingProgramDTOMapper;
        this.washingMachineDTOMapper = washingMachineDTOMapper;
    }

    @MethodWithMDC
    @Operation(
            summary = "Add new washing program",
            description = "Washing program name must be unique",
            tags = {"program"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Washing program created",
                    content = @Content(schema = @Schema(implementation = WashingProgramDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent or SQL constraints violated")
    })
    @PostMapping(
            value = "/program",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WashingProgramDTO> createWashingProgram(@RequestBody @Valid WashingProgramDTO washingProgram) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(washingProgramDTOMapper.mapEntityToDto(adminWashingMachineService.createNewWashingProgram(washingProgram)));
    }

    @MethodWithMDC
    @Operation(
            summary = "Add new washing machine model with programs (optionally)",
            description = "Washing machine model must be unique, programs array can be empty",
            tags = {"machine"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Washing machine created",
                    content = @Content(schema = @Schema(implementation = WashingMachineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent or SQL constraints violated")
    })
    @PostMapping(
            value = "/machine",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WashingMachineDTO> createWashingMachine(@RequestBody @Valid AddWashingMachineRequest addWashingMachineRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(washingMachineDTOMapper.mapEntityToDto(adminWashingMachineService.createNewWashingMachine(addWashingMachineRequest)));
    }
}
