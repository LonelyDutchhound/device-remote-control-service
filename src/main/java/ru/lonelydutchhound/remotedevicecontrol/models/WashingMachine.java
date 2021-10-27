package ru.lonelydutchhound.remotedevicecontrol.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "washing_machine")
@ToString
@NoArgsConstructor
public class WashingMachine implements AbstractSmartDevice<WashingProgram> {
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
}
