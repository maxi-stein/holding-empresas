package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpresaDAO implements EmpresaDAOInterface {

    private EntityManager entityManager;

    @Autowired
    public EmpresaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Empresa getById(Integer id) {
        return entityManager.find(Empresa.class,id);
    }

    @Override
    public Empresa getByIdConVendedores(Integer id) {
        //obtengo la empresa
        Empresa emp = entityManager.find(Empresa.class,id);

        //obtengo el listado de vendedores asociado a la empresa
        TypedQuery<Vendedor> vendedorTypedQuery = entityManager
                .createQuery("SELECT v FROM Vendedor v WHERE v.empresa = :emp", Vendedor.class);
        vendedorTypedQuery.setParameter("emp",emp);

        List<Vendedor> vendedores = vendedorTypedQuery.getResultList();

        List<Empleado> empleados = new ArrayList<>();

        //casteo los vendedores a Empleado
        for(Vendedor element : vendedores){
            empleados.add((Empleado) element);
        }

        //le cargo los vendedores a la empresa
        emp.setVendedores(empleados);

        return emp;
    }

    @Override
    public List<Empresa> getAll() {
        TypedQuery<Empresa> empresas = entityManager.createQuery("SELECT e FROM Empresa e ORDER BY e.nombre ASC",Empresa.class);
        return empresas.getResultList();
    }

    @Override
    public List<AreasMercado> getAreasMercadoPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.areasMercados WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);

        return empresaQuery.getSingleResult().getAreasMercados();
    }

    @Override
    public List<Empleado> getVendedoresPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.vendedores WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);

        return empresaQuery.getSingleResult().getVendedores();
    }

    @Override
    public List<Ciudad> getCiudadesPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.ciudades WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);

        return empresaQuery.getSingleResult().getCiudades();
    }

    @Override
    public List<Asesor> getAsesoresPorEmpresa(Empresa empr) {
        TypedQuery<Asesor> empresaQuery = entityManager.createQuery("SELECT e.asesor FROM AsesorEmpresa e WHERE e.empresa = :empr", Asesor.class);
        empresaQuery.setParameter("empr",empr);

        return empresaQuery.getResultList();
    }

    @Override
    @Transactional
    public void save(Empresa empresa) {
        entityManager.persist(empresa);
    }

    @Override
    @Transactional
    public void update(Empresa empresa) {
        entityManager.merge(empresa);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Empresa empresa = getById(id);
        empresa.setEliminado(1);
        update(empresa);
    }
}
