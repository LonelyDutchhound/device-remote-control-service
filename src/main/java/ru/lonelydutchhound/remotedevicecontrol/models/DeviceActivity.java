package ru.lonelydutchhound.remotedevicecontrol.models;

import org.hibernate.annotations.Type;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "device_activity")
public class DeviceActivity {
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
    private ProgramStatus programStatus;
}
