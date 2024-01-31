package inspt.steindilella.HoldingManagement;

import inspt.steindilella.HoldingManagement.dao.UbicacionesDAO;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;

@SpringBootApplication
public class HoldingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoldingManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UbicacionesDAO dao) {
		return runner -> {
			testSaveUbicacion(dao);
		};
	}

	private void testSaveUbicacion(UbicacionesDAO dao) {
		Pais pais = new Pais("Prueba",15.0,new BigInteger("100"));
		Ciudad ciudad = new Ciudad("Prueba City",pais);

		dao.save(pais,ciudad);
	}


}
