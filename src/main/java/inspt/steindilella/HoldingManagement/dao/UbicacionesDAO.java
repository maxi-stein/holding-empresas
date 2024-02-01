package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UbicacionesDAO implements UbicacionesDAOInterface {

    private EntityManager entityManager;

    @Autowired
    public UbicacionesDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Pais pais) {
        entityManager.persist(pais);
    }

    @Override
    @Transactional
    public void save(Ciudad ciudad) {
        entityManager.persist(ciudad);
    }

    @Override
    public Pais getPaisById(Integer id) {
        return entityManager.find(Pais.class,id);
    }

    @Override
    public Ciudad getCiudadById(Integer id) {
        return entityManager.find(Ciudad.class,id);
    }

    @Override
    public List<Pais> getAllPaises() {
        TypedQuery<Pais> paises = entityManager.createNamedQuery("SELECT e FROM Pais e ORDER BY e.nombre ASC", Pais.class);
        return paises.getResultList();
    }

    @Override
    public List<Ciudad> getAllCiudades() {
        TypedQuery<Ciudad> ciudades = entityManager.createNamedQuery("SELECT e FROM Ciudad e ORDER BY e.nombre ASC", Ciudad.class);
        return ciudades.getResultList();
    }

    @Override
    @Transactional
    public void updatePais(Pais pais) {
        entityManager.merge(pais);
    }

    @Override
    @Transactional
    public void updateCiudad(Ciudad ciudad) {
        entityManager.merge(ciudad);
    }

    @Override
    @Transactional
    public void deleteCiudad(Integer id) {
        Ciudad ciudad = getCiudadById(id);
        ciudad.setEliminado(1);
        updateCiudad(ciudad);
    }

    @Override
    @Transactional
    public void deletePais(Integer id) {
        Pais pais = getPaisById(id);
        pais.setEliminado(1);
        updatePais(pais);
    }
}
