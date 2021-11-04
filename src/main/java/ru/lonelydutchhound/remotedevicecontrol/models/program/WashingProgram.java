package ru.lonelydutchhound.remotedevicecontrol.models.program;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.lonelydutchhound.remotedevicecontrol.models.smartdevice.WashingMachine;

@Entity
@Table(name = "washing_program")
@ToString
@Getter
@NoArgsConstructor
public class WashingProgram implements Program {
  @Id
  @GeneratedValue
  private UUID id;
  @NotBlank(message = "Program name is mandatory")
  private String name;
  @NotNull(message = "Temperature setting is mandatory")
  @Min(value = 0)
  private int temperature;
  @Positive(message = "Duration must be more then 0 minutes")
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
  private Set<WashingMachine> washingMachineSet;

  private WashingProgram(WashingProgramBuilder builder) {
    this.name = builder.name;
    this.duration = builder.duration;
    this.temperature = builder.temperature;
    this.spinSpeed = builder.spinSpeed;
  }

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
      return new WashingProgram(this);
    }
  }
}
