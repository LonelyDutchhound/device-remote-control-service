package ru.lonelydutchhound.remotedevicecontrol.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private byte temperature;

    @NotNull(message = "Duration setting is mandatory")
    private Long duration;

    @Column(name = "spin_speed")
    private int spinSpeed;
}
