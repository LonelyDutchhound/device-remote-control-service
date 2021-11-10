package ru.lonelydutchhound.remotedevicecontrol.models.smartappliance;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;

@Entity
@Table(name = "washing_machine")
@ToString
@NoArgsConstructor
public class WashingMachine implements AbstractSmartAppliance<WashingProgram> {
  @Id
  @GeneratedValue
  private UUID id;
  @NotBlank(message = "Machine model is mandatory")
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
  private Set<WashingProgram> programSet = new HashSet<>();

  private WashingMachine(WashingMachineBuilder builder) {
    this.id = builder.uuid;
    this.model = builder.model;
    this.programSet = builder.programSet;
  }

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

  public void updateProgramSet(Set<WashingProgram> newProgramSet) {
    this.programSet = newProgramSet;
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
      return new WashingMachine(this);
    }
  }
}
