package ru.lonelydutchhound.remotedevicecontrol.models.program;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "washing_program")
@ToString
@Getter
@NoArgsConstructor
public class WashingProgram implements Program {
    private WashingProgram(WashingProgramBuilder builder){
        this.name = builder.name;
        this.duration = builder.duration;
        this.temperature = builder.temperature;
        this.spinSpeed = builder.spinSpeed;
    }
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Providing a name is mandatory")
    private String name;

    @NotNull(message = "Temperature setting is mandatory")
    @Min(value = 0)
    private int temperature;

    @Positive
    @NotNull(message = "Duration setting is mandatory")
    private Long duration;

    @Column(name = "spin_speed")
    private int spinSpeed;

    @ManyToMany
    @JoinTable(
            name = "washing_machine_program",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id"))
    @ToString.Exclude
    @JsonBackReference
    private Set<WashingMachine> washingMachineSet;

    @NoArgsConstructor
    public static class WashingProgramBuilder {
        private String name;
        private int temperature;
        private Long duration;
        private int spinSpeed;

        public WashingProgramBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public WashingProgramBuilder setTemperature(int temperature) {
            this.temperature = temperature;
            return this;
        }

        public WashingProgramBuilder setDuration(Long duration) {
            this.duration = duration;
            return this;
        }

        public WashingProgramBuilder setSpinSpeed(int spinSpeed) {
            this.spinSpeed = spinSpeed;
            return this;
        }

        public WashingProgram build() {
            WashingProgram washingProgram = new WashingProgram(this);

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            validator.validate(washingProgram);

            return washingProgram;
        }
    }
}
