package ru.lonelydutchhound.remotedevicecontrol.models.device;

import java.time.LocalDateTime;
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
import lombok.ToString;
import org.hibernate.annotations.Type;
import ru.lonelydutchhound.remotedevicecontrol.models.smartappliance.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.models.types.PowerStatus;

@Entity
@Table(name = "device")
@ToString
@Getter
@NoArgsConstructor
public class WashingMachineDevice implements Device {
  @Id
  @GeneratedValue
  private UUID id;
  @OneToOne
  @JoinColumn(name = "device_id", referencedColumnName = "id")
  private WashingMachine washingMachine;
  @Enumerated(EnumType.STRING)
  @Type(type = "ru.lonelydutchhound.remotedevicecontrol.utils.EnumTypePostgreSql")
  private PowerStatus powerStatus;
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private final LocalDateTime createdAt = LocalDateTime.now();
  @Column(name = "deleted_at", columnDefinition = "timestamp with time zone")
  private LocalDateTime deletedAt;

  public WashingMachineDevice(WashingMachine washingMachine) {
    this();
    this.washingMachine = washingMachine;
    this.powerStatus = PowerStatus.ON;
  }

  public void updatePowerStatus(PowerStatus status) {
    this.powerStatus = status;
  }
}
