package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.EmpresaDAOInterface;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService implements EmpresaServiceInterface{

    EmpresaDAOInterface empresaDAO;

    @Autowired
    public EmpresaService(EmpresaDAOInterface empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    @Override
    public Empresa getById(Integer id) {
        return empresaDAO.getById(id);
    }

    @Override
    public List<Empresa> getAll() {
        return empresaDAO.getAll();
    }

    @Override
    public List<AreasMercado> getAreasMercadoPorEmpresa(Integer id) {
        return empresaDAO.getAreasMercadoPorEmpresa(id);
    }

    @Override
    public List<Empleado> getVendedoresPorEmpresa(Integer id) {
        return empresaDAO.getVendedoresPorEmpresa(id);
    }

    @Override
    public List<Ciudad> getCiudadesPorEmpresa(Integer id) {
        return empresaDAO.getCiudadesPorEmpresa(id);
    }

    @Override
    public List<Empleado> getAsesoresPorEmpresa(Integer id) {
        return empresaDAO.getAsesoresPorEmpresa(id);
    }

    @Override
    public void save(Empresa empresa) {
        empresaDAO.save(empresa);
    }

    @Override
    public void update(Empresa empresa) {
        empresaDAO.update(empresa);
    }

    @Override
    public void delete(Integer id) {
        empresaDAO.delete(id);
    }
}
