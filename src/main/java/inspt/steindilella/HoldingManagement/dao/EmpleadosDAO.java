package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Asesor;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpleadosDAO implements EmpleadosDAOInterface{

    private EntityManager entityManager;

    @Autowired
    public EmpleadosDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Empleado getById(Integer id) {
        return entityManager.find(Empleado.class,id);
    }

    @Override
    public List<Empleado> getAll() {
        TypedQuery<Empleado> query = entityManager.createQuery("SELECT e FROM Empleado e",Empleado.class);
        return query.getResultList();
    }

    @Override
    public List<Empleado> getEmpleadosPorEmpresa(Integer id) {
        //obtengo la empresa para acceder a los vendedores
        TypedQuery<Empresa> queryEmpresa = entityManager
                .createQuery("SELECT e FROM Empresa e WHERE e.id = :idEmpresa",Empresa.class);
        queryEmpresa.setParameter("idEmp",id);

        //obtengo los asesores a traves de AsesorEmpresa
        TypedQuery<Asesor> queryAsesor = entityManager
                .createQuery("SELECT ae.asesor FROM AsesorEmpresa ae JOIN Empresa e WHERE e.id = :idEmp ", Asesor.class);
        queryAsesor.setParameter("idEmp",id);

        Empresa emp =queryEmpresa.getSingleResult();
        List<Empleado> empleados = emp.getVendedores();
        empleados.addAll(queryAsesor.getResultList());

        return empleados;
    }

    @Override
    @Transactional
    public void save(Empleado emp) {
        entityManager.persist(emp);
    }

    @Override
    @Transactional
    public void update(Empleado emp) {
        entityManager.merge(emp);
    }

    @Override
    @Transactional
    public void delete(Empleado emp) {
        emp.setEliminado(1);
        entityManager.merge(emp);
    }

}
