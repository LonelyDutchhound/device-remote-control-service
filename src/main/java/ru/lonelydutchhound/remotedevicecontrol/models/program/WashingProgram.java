package ru.lonelydutchhound.remotedevicecontrol.models.program;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.lonelydutchhound.remotedevicecontrol.models.program.Program;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "washing_program")
@ToString
@Getter
@NoArgsConstructor
public class WashingProgram implements Program {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Providing a name is mandatory")
    private String name;

    @NotNull(message = "Temperature setting is mandatory")
    private int temperature;

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
}
