package ru.lonelydutchhound.remotedevicecontrol.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import ru.lonelydutchhound.remotedevicecontrol.models.types.PowerStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deleted_at", columnDefinition = "timestamp with time zone")
    private LocalDateTime deletedAt;
}
