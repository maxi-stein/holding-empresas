package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

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
    public Set<Empresa> getAll() {
        TypedQuery<Empresa> empresas = entityManager.createQuery("SELECT e FROM Empresa e ORDER BY e.nombre ASC",Empresa.class);
        Set<Empresa> listALL = new HashSet<>(empresas.getResultList());
        return listALL;
    }

    @Override
    public Set<AreasMercado> getAreasMercadoPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.areasMercados WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);
        Set<AreasMercado> listado = new HashSet<>(empresaQuery.getSingleResult().getAreasMercados());

        return listado;
    }

    @Override
    public Set<Empleado> getVendedoresPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.vendedores WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);
        Set listado = new HashSet<>(empresaQuery.getSingleResult().getVendedores());

        return listado;
    }

    @Override
    public Set<Ciudad> getCiudadesPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.ciudades WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);
        Set listado = new HashSet<>(empresaQuery.getSingleResult().getCiudades());

        return listado;
    }

    @Override
    public Set<Asesor> getAsesoresPorEmpresa(Empresa empr) {
        TypedQuery<Asesor> empresaQuery = entityManager.createQuery("SELECT e.asesor FROM AsesorEmpresa e WHERE e.empresa = :empr", Asesor.class);
        empresaQuery.setParameter("empr",empr);

        Set<Asesor> listado = new HashSet<>(empresaQuery.getResultList());

        return listado;
    }

    @Override
    public Set<AsesorEmpresa> getAsesoresPorEmpresaConFechaInicio(Empresa empr) {
        TypedQuery<AsesorEmpresa> empresaQuery = entityManager
                .createQuery("SELECT e.asesores FROM Empresa e WHERE e.id = :idEmpr", AsesorEmpresa.class);
        empresaQuery.setParameter("idEmpr",empr.getId());

        Set<AsesorEmpresa> listado = new HashSet<>(empresaQuery.getResultList());

        return listado;
    }

    @Override
    @Transactional
    public void save(Empresa empresa) {
        entityManager.persist(empresa);
    }

    @Override
    @Transactional
    public void save(AsesorEmpresa asesorEmpresa) {
        entityManager.persist(asesorEmpresa);
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

    @Override
    @Transactional
    public void delete(AsesorEmpresa asesorEmpresa) {
        entityManager.remove(asesorEmpresa);
        asesorEmpresa.getEmpresa().getAsesores().remove(asesorEmpresa);
        asesorEmpresa.getAsesor().getEmpresasAsesoradas().remove(asesorEmpresa);
    }
}
