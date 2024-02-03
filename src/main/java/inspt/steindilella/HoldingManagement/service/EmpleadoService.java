package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.EmpleadosDAOInterface;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.entity.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpleadoService implements EmpleadoServiceInterface{

    private EmpleadosDAOInterface empDao;

    @Autowired
    public EmpleadoService(EmpleadosDAOInterface empDao) {
        this.empDao = empDao;
    }

    @Override
    public Empleado getById(Integer id) {
        return empDao.getById(id);
    }

    @Override
    public List<Empleado> getAll() {
        return empDao.getAll();
    }

    @Override
    public List<Empleado> getEmpleadosPorEmpresa(Integer id) {
        return empDao.getEmpleadosPorEmpresa(id);
    }

    @Override
    public LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa) {
        return empDao.getFechaAsesorEmpresa(idAsesor, idEmpresa);
    }

    @Override
    public LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado) {
        return empDao.getFechaCaptado(idPadre,idCaptado);
    }
    
    @Override
    public List<Empresa> getEmpresasAsesoradas(Integer id) {
        return empDao.getEmpresasAsesoradas(id);
    }
    @Override
    public List<Vendedor> getVendedoresCaptados(Integer idPadre) {
        return empDao.getVendedoresCaptados(idPadre);
    }

    @Override
    public void save(Empleado emp) {
        empDao.save(emp);
    }

    @Override
    public void update(Empleado emp) {
        empDao.update(emp);
    }

    @Override
    public void delete(Empleado emp) {
        empDao.delete(emp);
    }
}
