package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.*;

import java.time.LocalDate;
import java.util.Set;

public interface EmpresaServiceInterface {
    Empresa getById(Integer id);
    Set<Empresa> getAll();
    Set<AreasMercado> getAreasMercadoPorEmpresa(Integer id);
    Set<Empleado> getVendedoresPorEmpresa(Integer id);
    Set<Ciudad> getCiudadesPorEmpresa(Integer id);
    Set<Asesor> getAsesoresPorEmpresa(Integer id);

    void agregarVendedor(Vendedor vendedor, Integer id);
    void desvincularVendedor(Vendedor vendedor, Integer id);

    void agregarAsesor(Asesor asesor, LocalDate fechaInicio, Integer idEmpresa);
    void desvincularAsesor(Asesor asesor,Integer idEmpresa);

    void save(Empresa empresa);
    void update(Empresa empresa);
    void delete(Integer id);


}
