package inspt.steindilella.HoldingManagement;

import inspt.steindilella.HoldingManagement.dao.AreasMercadoDAOInterface;
import inspt.steindilella.HoldingManagement.dao.UbicacionesDAOInterface;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
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
	public CommandLineRunner commandLineRunner(AreasMercadoDAOInterface areaDao, UbicacionesDAOInterface ubiDao) {
		return runner -> {
			//testUbicacion(ubiDao);
			//testArea(areaDao);
		};
	}

	private void testArea(AreasMercadoDAOInterface areaDao) {
		AreasMercado areaPrueba = new AreasMercado("Consultora Financiera", "Analistas de mercados financieros.");
		areaDao.save(areaPrueba);
	}

	private void testUbicacion(UbicacionesDAOInterface ubiDao) {
		Pais pais = new Pais("Uruguay",1500.0,new BigInteger("100000"));
		Ciudad ciudad = new Ciudad("Montevideo",pais);

		ubiDao.save(ciudad);
	}

}
