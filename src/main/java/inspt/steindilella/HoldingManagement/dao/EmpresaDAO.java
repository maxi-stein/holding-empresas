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
    public Empresa getEmpresaByVendedorId(Integer id) {
        TypedQuery<Empresa> query = entityManager
                .createQuery("SELECT v.empresa FROM Vendedor v WHERE v.id = :id", Empresa.class);
        query.setParameter("id",id);

        return query.getSingleResult();
    }

    @Override
    public Set<Empresa> getAll() {
        TypedQuery<Empresa> empresas = entityManager.createQuery("SELECT e FROM Empresa e ORDER BY e.nombre ASC",Empresa.class);
        return new HashSet<>(empresas.getResultList());
    }

    @Override
    public Set<AreasMercado> getAreasMercadoPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.areasMercados WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);

        return new HashSet<>(empresaQuery.getSingleResult().getAreasMercados());
    }

    @Override
    public Set<Vendedor> getVendedoresPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.vendedores WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);
        Set<Vendedor> coleccion = null;
        try {
            coleccion = new HashSet<>(empresaQuery.getSingleResult().getVendedores());
        }catch (Exception e){
            System.out.println("Empresa sin Vendedores");
        }

        return coleccion;
    }

    @Override
    public Set<Ciudad> getCiudadesPorEmpresa(Integer id) {
        TypedQuery<Empresa> empresaQuery = entityManager.createQuery("SELECT e FROM Empresa e JOIN FETCH e.ciudades WHERE e.id = :idEmpresa", Empresa.class);
        empresaQuery.setParameter("idEmpresa",id);

        return new HashSet<>(empresaQuery.getSingleResult().getCiudades());
    }

    @Override
    public Set<Asesor> getAsesoresPorEmpresa(Empresa empr) {
        TypedQuery<Asesor> empresaQuery = entityManager.createQuery("SELECT e.asesor FROM AsesorEmpresa e WHERE e.empresa = :empr", Asesor.class);
        empresaQuery.setParameter("empr",empr);

        return new HashSet<>(empresaQuery.getResultList());
    }

    @Override
    public Set<AsesorEmpresa> getAsesoresPorEmpresaConFechaInicio(Empresa empr) {
        TypedQuery<AsesorEmpresa> empresaQuery = entityManager
                .createQuery("SELECT e.asesores FROM Empresa e WHERE e.id = :idEmpr", AsesorEmpresa.class);
        empresaQuery.setParameter("idEmpr",empr.getId());

        return new HashSet<>(empresaQuery.getResultList());
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
    public void desbloquear(Integer id){
        Empresa empresa = getById(id);
        empresa.setEliminado(0);
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
