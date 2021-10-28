package ru.lonelydutchhound.remotedevicecontrol.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.UUID;

@Entity
@Table(name = "device_activity")
@Getter
@NoArgsConstructor
public class DeviceActivity {
    private DeviceActivity(DeviceActivityBuilder builder) {
        this.id = builder.uuid;
        this.washingMachineDevice = builder.washingMachineDevice;
        this.program = builder.washingProgram;
        this.programStatus = builder.programStatus;
    }

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

        public DeviceActivityBuilder setWashingMachineDevice(WashingMachineDevice washingMachineDevice) {
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

        public DeviceActivity build() {
            DeviceActivity deviceActivity = new DeviceActivity(this);

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            validator.validate(deviceActivity);

            return deviceActivity;
        }
    }
}
