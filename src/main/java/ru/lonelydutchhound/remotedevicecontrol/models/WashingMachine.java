package ru.lonelydutchhound.remotedevicecontrol.models;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
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

    @OneToMany
    @JoinTable(
            name = "washing_machine_program",
            joinColumns = @JoinColumn(name = "machine_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id"))
    @ToString.Exclude
    private List<WashingProgram> programList;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public List<WashingProgram> getProgramList() {
        return this.programList;
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
