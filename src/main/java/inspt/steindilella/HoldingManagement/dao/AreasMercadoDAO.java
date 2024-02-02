package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AreasMercadoDAO implements AreasMercadoDAOInterface {

    private EntityManager entityManager;
    @Autowired
    public AreasMercadoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AreasMercado getById(Integer id) {
        return entityManager.find(AreasMercado.class, id);
    }

    @Override
    public List<AreasMercado> getAll() {
        TypedQuery<AreasMercado> getAll = entityManager.createQuery("SELECT e FROM AreasMercado e ORDER BY e.nombre ASC", AreasMercado.class);
        return getAll.getResultList();
    }

    @Override
    @Transactional
    public void save(AreasMercado area) {
        entityManager.persist(area);
    }

    @Override
    @Transactional
    public void update(AreasMercado area) {
        entityManager.merge(area);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        AreasMercado area = getById(id);
        area.setEliminado(1);
        update(area);
    }
}
