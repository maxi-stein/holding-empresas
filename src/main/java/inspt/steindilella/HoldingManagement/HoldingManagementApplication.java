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

import java.time.LocalDate;
import java.util.Set;

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
			//testAgregarVendedor(emp,esiDao);
			//testDesvincularVendedor(emp,esiDao);
			//testAgregarAsesor(emp);
			//testDesvincularAsesor(emp,esiDao);
			testAgregarVendedorCaptado(esiDao);
		};
	}

	private void testAgregarVendedorCaptado(EmpleadoServiceInterface esiDao) {
		esiDao.agregarVendedorCaptado(3,4, LocalDate.now());
	}

	private void testDesvincularAsesor(EmpresaServiceInterface emp, EmpleadoServiceInterface esiDao) {
		emp.desvincularAsesor((Asesor) esiDao.getById(19),2);
	}

	private void testAgregarAsesor(EmpresaServiceInterface emp) {
		Asesor asesor = new Asesor("Armado","Diaz","Sistemas","Callejon 1B");
		System.out.println("El Hash del Asesor es: "+ asesor.hashCode());
		Set<Asesor> set = emp.getAsesoresPorEmpresa(2);
		for(Asesor element : set){
			System.out.println(element.toString()+" Hash: "+element.hashCode());
		}

		emp.agregarAsesor(asesor,
				LocalDate.now(),2);

	}

	private void testDesvincularVendedor(EmpresaServiceInterface emp, EmpleadoServiceInterface esiDao) {
		Vendedor vendedor = (Vendedor) esiDao.getById(3);
		emp.desvincularVendedor(vendedor,3);
	}

	private void testAgregarVendedor(EmpresaServiceInterface emp, EmpleadoServiceInterface esiDao) {
		Vendedor vendedor = (Vendedor) esiDao.getById(3);
		emp.agregarVendedor(vendedor,3);
	}

	private void testArea(AreasMercadoServiceInterface areaDao) {
		//AreasMercado areaPrueba = new AreasMercado("Consultora Financiera", "Analistas de mercados financieros.");
		//areaDao.save(areaPrueba);
		Set<AreasMercado> listado = areaDao.getAll();
		for (AreasMercado areasMercado : listado) {
			System.out.println(areasMercado.getDescripcion());
		}

	}

	private void testUbicacion(UbicacionesServiceInterface ubiDao) {
		//System.out.println(ubiDao.getAllPaises());
		//System.out.println(ubiDao.getAllCiudades());
		//Pais pais = new Pais("Mexico",4500.0,new BigInteger("40000000"));
		Pais p = ubiDao.getPaisById(4);
		Ciudad ciudad = new Ciudad("Ciudad de Mexico",p);
		p.setCapital(ciudad);
		ubiDao.updatePais(p);
		//ubiDao.saveCiudad(ciudad);
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
		Set<Empresa> listEmpresas = esiDao.getEmpresasAsesoradas(12);

		for(Empresa element : listEmpresas){
			System.out.println("Empresa: "+element.getNombre());
			System.out.println("Fecha Inicio: "+esiDao.getFechaAsesorEmpresa(12, element.getId()));
		}
	}

	public void testVendedores(EmpleadoServiceInterface esiDao){
		Set<Vendedor> listCaptados = esiDao.getVendedoresCaptados(4);

		for(Vendedor v : listCaptados){
			System.out.println("Vendedor captado: "+ v.getNombre()+" "+v.getApellido());
			System.out.println("Fecha captacion: "+esiDao.getFechaCaptado(4,v.getId()));
		}
	}

}
