package com.musala.droneservice;

import com.musala.droneservice.repositories.DroneRepository;
import com.musala.droneservice.services.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class DroneServiceApplicationTests {

	@Autowired
	DroneService droneService;
	@MockBean
	DroneRepository droneRepository;

}
