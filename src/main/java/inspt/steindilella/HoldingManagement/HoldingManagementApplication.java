package inspt.steindilella.HoldingManagement;

import inspt.steindilella.HoldingManagement.entity.*;
import inspt.steindilella.HoldingManagement.service.AreasMercadoServiceInterface;
import inspt.steindilella.HoldingManagement.service.EmpleadoServiceInterface;
import inspt.steindilella.HoldingManagement.service.EmpresaServiceInterface;
import inspt.steindilella.HoldingManagement.service.UbicacionesServiceInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.util.List;

@SpringBootApplication
public class HoldingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoldingManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AreasMercadoServiceInterface areaDao,
											   UbicacionesServiceInterface ubiDao,
											   EmpresaServiceInterface emp,
											   EmpleadoServiceInterface esiDao) {
		return runner -> {
			//testUbicacion(ubiDao);
			//testArea(areaDao);
			//testEmpresa(emp, ubiDao);
			//testAsesor(emp);
			//testEmpresaAsesorada(esiDao);
			//testVendedores(esiDao);
			testAgregarVendedor(emp,esiDao);
		};
	}

	private void testAgregarVendedor(EmpresaServiceInterface emp, EmpleadoServiceInterface esiDao) {
		Vendedor vendedor = (Vendedor) esiDao.getById(3);
		emp.agregarVendedorAEmpresa(vendedor,3);
	}

	private void testArea(AreasMercadoServiceInterface areaDao) {
		AreasMercado areaPrueba = new AreasMercado("Consultora Financiera", "Analistas de mercados financieros.");
		areaDao.save(areaPrueba);
	}

	private void testUbicacion(UbicacionesServiceInterface ubiDao) {
		Pais pais = new Pais("Uruguay",1500.0,new BigInteger("100000"));
		Ciudad ciudad = new Ciudad("Montevideo",pais);
		ubiDao.saveCiudad(ciudad);
		System.out.println(ubiDao.getAllPaises());
		System.out.println(ubiDao.getAllCiudades());

	}

	private void testEmpresa(EmpresaServiceInterface emp, UbicacionesServiceInterface ubi){
		//Ciudad ciudad = ubi.getCiudadById(2);
		//emp.save(new Empresa("Empresita",ciudad, LocalDate.now(),new BigDecimal("123485674.5")));

		Empresa empresa = emp.getById(2);
		System.out.println(emp.getVendedoresPorEmpresa(2));
		System.out.println(emp.getAreasMercadoPorEmpresa(2));
		System.out.println(emp.getCiudadesPorEmpresa(2));
	}

	private void testAsesor(EmpresaServiceInterface emp){
		System.out.println(emp.getAreasMercadoPorEmpresa(2));
		System.out.println(emp.getAsesoresPorEmpresa(2));
	}

	public void testEmpresaAsesorada(EmpleadoServiceInterface esiDao){
		List<Empresa> listEmpresas = esiDao.getEmpresasAsesoradas(12);

		for(Empresa element : listEmpresas){
			System.out.println("Empresa: "+element.getNombre());
			System.out.println("Fecha Inicio: "+esiDao.getFechaAsesorEmpresa(12, element.getId()));
		}
	}

	public void testVendedores(EmpleadoServiceInterface esiDao){
		List<Vendedor> listCaptados = esiDao.getVendedoresCaptados(4);

		for(Vendedor v : listCaptados){
			System.out.println("Vendedor captado: "+ v.getNombre()+" "+v.getApellido());
			System.out.println("Fecha captacion: "+esiDao.getFechaCaptado(4,v.getId()));
		}
	}

}
