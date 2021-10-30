package ru.lonelydutchhound.remotedevicecontrol.models.smartDevice;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "washing_machine")
@ToString
@NoArgsConstructor
public class WashingMachine implements AbstractSmartDevice<WashingProgram> {
    private WashingMachine(WashingMachineBuilder builder) {
        this.id = builder.uuid;
        this.model = builder.model;
    }

    @Id
    @GeneratedValue
    private UUID id;

    private String model;

    @ManyToMany
    @JoinTable(
            name = "washing_machine_program",
            joinColumns = @JoinColumn(name = "machine_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id"))
    @ToString.Exclude
    @JsonManagedReference
    private Set<WashingProgram> programSet;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public Set<WashingProgram> getProgramSet() {
        return this.programSet;
    }

    @NoArgsConstructor
    public static class WashingMachineBuilder {
        private UUID uuid;
        private String model;

        public WashingMachineBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public WashingMachineBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public WashingMachine build() {
            WashingMachine washingMachine = new WashingMachine(this);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            validator.validate(washingMachine);

            return washingMachine;
        }
    }
}
