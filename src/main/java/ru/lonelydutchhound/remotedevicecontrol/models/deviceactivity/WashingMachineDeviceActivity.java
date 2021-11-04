package ru.lonelydutchhound.remotedevicecontrol.models.deviceactivity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

@Entity
@Table(name = "device_activity")
@Getter
@NoArgsConstructor
public class WashingMachineDeviceActivity implements DeviceActivity<WashingMachineDevice> {
  @Id
  @GeneratedValue
  private UUID id;
  @OneToOne
  @JoinColumn(name = "device_id", referencedColumnName = "id")
  private WashingMachineDevice washingMachineDevice;
  @OneToOne
  @JoinColumn(name = "program_id", referencedColumnName = "id")
  private WashingProgram program;
  @Enumerated(EnumType.STRING)
  @Type(type = "ru.lonelydutchhound.remotedevicecontrol.utils.EnumTypePostgreSql")
  @Column(name = "program_status")
  private ProgramStatus programStatus;

  private WashingMachineDeviceActivity(DeviceActivityBuilder builder) {
    this.id = builder.uuid;
    this.washingMachineDevice = builder.washingMachineDevice;
    this.program = builder.washingProgram;
    this.programStatus = builder.programStatus;
  }

  @NoArgsConstructor
  public static class DeviceActivityBuilder {
    private UUID uuid;
    private WashingMachineDevice washingMachineDevice;
    private WashingProgram washingProgram;
    private ProgramStatus programStatus;

    public DeviceActivityBuilder setUuid(UUID uuid) {
      this.uuid = uuid;
      return this;
    }

    public DeviceActivityBuilder setWashingMachineDevice(
        WashingMachineDevice washingMachineDevice) {
      this.washingMachineDevice = washingMachineDevice;
      return this;
    }

    public DeviceActivityBuilder setWashingProgram(WashingProgram washingProgram) {
      this.washingProgram = washingProgram;
      return this;
    }

    public DeviceActivityBuilder setProgramStatus(ProgramStatus programStatus) {
      this.programStatus = programStatus;
      return this;
    }

    public WashingMachineDeviceActivity build() {
      return new WashingMachineDeviceActivity(this);
    }
  }
}
