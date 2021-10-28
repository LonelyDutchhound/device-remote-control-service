package ru.lonelydutchhound.remotedevicecontrol;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

@SpringBootTest
public class TestFixtures {
    private final WashingProgramRepository washingProgramRepository;

    @Autowired
    public TestFixtures(WashingProgramRepository washingProgramRepository){
        this.washingProgramRepository = washingProgramRepository;
    }

    @Test
    private void generateTestData() {
        // seed data to repository;
    }
}
