package inspt.steindilella.HoldingManagement;

import inspt.steindilella.HoldingManagement.dao.AreasMercadoDAO;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HoldingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoldingManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AreasMercadoDAO dao) {
		return runner -> {
			testSaveUbicacion(dao);
		};
	}

	private void testSaveUbicacion(AreasMercadoDAO dao) {
		//Pais pais = new Pais("Uruguay",1500.0,new BigInteger("100000"));
		//Ciudad ciudad = new Ciudad("Montevideo",pais);

		//dao.save(pais,ciudad);

		AreasMercado areaPrueba = new AreasMercado("Consultora Financiera", "Analistas de mercados financieros.");
		dao.save(areaPrueba);
	}


}
