package ru.lonelydutchhound.remotedevicecontrol.web.controllers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDto;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingProgramDto;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDtoMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingProgramDtoMapper;
import ru.lonelydutchhound.remotedevicecontrol.logging.MethodWithMdc;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.services.admin.WashingMachineAdminService;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.AddWashingMachineRequest;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.CreateWashingProgramRequest;

@RestController
@RequestMapping("/api/v1/admin/washing-machine")
@Tag(name = "Admin control panel", description = "Customise user devices")
public class AdminDeviceController {
  private final Logger logger = LoggerFactory.getLogger(AdminDeviceController.class);

  private final WashingMachineAdminService washingMachineAdminService;
  private final WashingProgramDtoMapper washingProgramDtoMapper;
  private final WashingMachineDtoMapper
      washingMachineDtoMapper;

  @Autowired
  public AdminDeviceController(
      WashingMachineAdminService washingMachineAdminService,
      WashingProgramDtoMapper washingProgramDtoMapper,
      WashingMachineDtoMapper washingMachineDtoMapper
  ) {
    this.washingMachineAdminService = washingMachineAdminService;
    this.washingProgramDtoMapper = washingProgramDtoMapper;
    this.washingMachineDtoMapper = washingMachineDtoMapper;
  }

  @MethodWithMdc
  @Operation(
      summary = "Add new washing program",
      description = "Washing program name must be unique",
      tags = {"program"}
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Washing program created",
          content = @Content(schema = @Schema(implementation = WashingProgramDto.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Request parameter is not valid or absent or SQL constraints violated"
      )
  })
  @PostMapping(
      value = "/programs",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<WashingProgramDto> createWashingProgram(
      @RequestBody @Valid CreateWashingProgramRequest request
  ) {
    logger.info("Request start proceeding");
    var washingProgram = new WashingProgram.WashingProgramBuilder()
        .setName(request.getName())
        .setDuration(request.getDuration())
        .setTemperature(request.getTemperature())
        .setSpinSpeed(request.getSpinSpeed())
        .build();
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(washingProgramDtoMapper.mapEntityToDto(
            washingMachineAdminService.createProgram(washingProgram)));
  }

  @MethodWithMdc
  @Operation(
      summary = "Get information about all programs added",
      description = "Full information about programs include parameters",
      tags = {"program"}
  )
  @GetMapping(
      value = "/programs",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<WashingProgramDto>> getAllPrograms() {
    logger.info("Request start proceeding");
    var washingProgramDtos = washingMachineAdminService.getAllPrograms()
        .stream().map(washingProgramDtoMapper::mapEntityToDto).collect(Collectors.toList());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(washingProgramDtos);
  }

  @MethodWithMdc
  @Operation(
      summary = "Add new washing machine model with programs (optionally)",
      description = "Washing machine model must be unique, programs array can be empty",
      tags = {"machine"}
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Washing machine created",
          content = @Content(schema = @Schema(implementation = WashingMachineDto.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Request parameter is not valid or absent or SQL constraints violated"
      )
  })
  @PostMapping(
      value = "/machines",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<WashingMachineDto> createWashingMachine(
      @RequestBody @Valid AddWashingMachineRequest request
  ) {
    logger.info("Request start proceeding");
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(washingMachineDtoMapper.mapEntityToDto(
            washingMachineAdminService.createNewSmartDevice(request.getModel(),
                request.getProgramIdList())));
  }
}
