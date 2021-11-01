package ru.lonelydutchhound.remotedevicecontrol.models.smartDevice;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "washing_machine")
@ToString
@NoArgsConstructor
public class WashingMachine implements AbstractSmartDevice<WashingProgram> {
    private WashingMachine(WashingMachineBuilder builder) {
        this.id = builder.uuid;
        this.model = builder.model;
        this.programSet = builder.programSet;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty
    @NotNull
    private String model;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "washing_machine_program",
            joinColumns = @JoinColumn(name = "machine_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id"))
    @ToString.Exclude
    @JsonManagedReference
    private Set<WashingProgram> programSet = new HashSet<>();

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
        private Set<WashingProgram> programSet;

        public WashingMachineBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public WashingMachineBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public WashingMachineBuilder setProgramSet(Set<WashingProgram> washingProgramSet) {
            this.programSet = washingProgramSet;
            return this;
        }

        public WashingMachine build() {
            WashingMachine washingMachine = new WashingMachine(this);

            // TODO: implement validator bean, take validation out
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            var violations = validator.validate(washingMachine);
            if (!violations.isEmpty()) {
                throw new ValidationException(violations.stream()
                        .map(violation -> violation.getPropertyPath() + " " + violation.getMessage()).collect(Collectors.toList()).toString());
            }

            return washingMachine;
        }
    }
}
