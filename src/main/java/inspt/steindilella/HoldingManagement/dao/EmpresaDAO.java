package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    public List<Empresa> getAll() {
        TypedQuery<Empresa> empresas = entityManager.createQuery("SELECT e FROM Empresa e ORDER BY e.nombre ASC",Empresa.class);
        return empresas.getResultList();
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
