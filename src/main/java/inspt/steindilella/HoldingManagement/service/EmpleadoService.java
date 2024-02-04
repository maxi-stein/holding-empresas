package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.EmpleadosDAOInterface;
import inspt.steindilella.HoldingManagement.dao.EmpresaDAOInterface;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.entity.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Service
public class EmpleadoService implements EmpleadoServiceInterface{

    private EmpleadosDAOInterface empleadoDao;
    private EmpresaDAOInterface empresaDao;

    @Autowired
    public EmpleadoService(EmpleadosDAOInterface empDao, EmpresaDAOInterface empresaDao) {
        this.empleadoDao = empDao;
        this.empresaDao = empresaDao;
    }

    @Override
    public Empleado getById(Integer id) {
        return empleadoDao.getById(id);
    }

    @Override
    public Set<Empleado> getAll() {
        return empleadoDao.getAll();
    }

    @Override
    public Set<Empleado> getEmpleadosPorEmpresa(Integer id) {
        return empleadoDao.getEmpleadosPorEmpresa(id);
    }

    @Override
    public LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa) {
        return empleadoDao.getFechaAsesorEmpresa(idAsesor, idEmpresa);
    }

    @Override
    public LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado) {
        return empleadoDao.getFechaCaptado(idPadre,idCaptado);
    }
    
    @Override
    public Set<Empresa> getEmpresasAsesoradas(Integer id) {
        return empleadoDao.getEmpresasAsesoradas(id);
    }
    @Override
    public Set<Vendedor> getVendedoresCaptados(Integer idPadre) {
        return empleadoDao.getVendedoresCaptados(idPadre);
    }

    @Override
    public void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado) {
        //obtengo las empresas a las cuales trabaja cada vendedor
        Integer idEmpresaVendedor = empresaDao.getEmpresaByVendedorId(idVendedor).getId();
        Integer idEmpresaVendedorCaptado = empresaDao.getEmpresaByVendedorId(idVendedorCaptado).getId();

        //verifico que sean las mismas
        if(Objects.equals(idEmpresaVendedor, idEmpresaVendedorCaptado)){

            //verifico que el vendedorCaptado no haya previamente captado al vendedor padre o alguno de sus captadores
            if(){
                //todo: aplicar alguna recursividad para evaluar
            }
            else{
                empleadoDao.agregarVendedorCaptado(idVendedor,idVendedorCaptado,fechaCaptado);
            }
        }
        else{
            //todo: manejar exception vendedores distintas empresas
            System.out.println("Los vendedores no pertenecen a la misma empresa");
        }
    }

    @Override
    public void save(Empleado emp) {
        empleadoDao.save(emp);
    }

    @Override
    public void update(Empleado emp) {
        empleadoDao.update(emp);
    }

    @Override
    public void delete(Empleado emp) {
        empleadoDao.delete(emp);
    }
}
