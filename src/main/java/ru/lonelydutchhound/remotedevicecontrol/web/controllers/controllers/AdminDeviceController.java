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
import org.springframework.web.bind.annotation.*;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingProgramDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingProgramDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.logging.MethodWithMDC;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.services.admin.WashingMachineAdminService;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.AddWashingMachineRequest;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.CreateWashingProgramRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/washing-machine")
@Tag(name = "Admin control panel", description = "Customise user devices")
public class AdminDeviceController {
  private final WashingMachineAdminService washingMachineAdminService;
  private final WashingProgramDTOMapper washingProgramDTOMapper;
  private final WashingMachineDTOMapper washingMachineDTOMapper;

  @Autowired
  public AdminDeviceController (
      WashingMachineAdminService washingMachineAdminService,
      WashingProgramDTOMapper washingProgramDTOMapper,
      WashingMachineDTOMapper washingMachineDTOMapper
  ) {
    this.washingMachineAdminService = washingMachineAdminService;
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
      value = "/programs",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<WashingProgramDTO> createWashingProgram (@RequestBody @Valid CreateWashingProgramRequest request) {
    var washingProgram = new WashingProgram.WashingProgramBuilder()
        .setName(request.getName())
        .setDuration(request.getDuration())
        .setTemperature(request.getTemperature())
        .setSpinSpeed(request.getSpinSpeed())
        .build();
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(washingProgramDTOMapper.mapEntityToDto(washingMachineAdminService.createProgram(washingProgram)));
  }

  @MethodWithMDC
  @Operation(
      summary = "Get information about all programs added",
      description = "Full information about programs include parameters",
      tags = {"program"}
  )
  @GetMapping(
      value = "/programs",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<WashingProgramDTO>> getAllPrograms () {
    var washingProgramDTOList = washingMachineAdminService.getAllPrograms()
        .stream().map(washingProgramDTOMapper::mapEntityToDto).collect(Collectors.toList());

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(washingProgramDTOList);
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
      value = "/machines",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<WashingMachineDTO> createWashingMachine (@RequestBody @Valid AddWashingMachineRequest request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(washingMachineDTOMapper.mapEntityToDto(washingMachineAdminService.createNewSmartDevice(request.getModel(), request.getProgramIdList())));
  }
}
