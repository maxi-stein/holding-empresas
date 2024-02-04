package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Set<Empleado> getAll() {
        TypedQuery<Empleado> query = entityManager.createQuery("SELECT e FROM Empleado e",Empleado.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Set<Empleado> getEmpleadosPorEmpresa(Integer id) {
        //obtengo la empresa para acceder a los vendedores
        TypedQuery<Empresa> queryEmpresa = entityManager
                .createQuery("SELECT e FROM Empresa e WHERE e.id = :idEmpresa",Empresa.class);
        queryEmpresa.setParameter("idEmp",id);

        //obtengo los asesores a traves de AsesorEmpresa
        TypedQuery<Asesor> queryAsesor = entityManager
                .createQuery("SELECT ae.asesor FROM AsesorEmpresa ae JOIN Empresa e WHERE e.id = :idEmp ", Asesor.class);
        queryAsesor.setParameter("idEmp",id);

        Empresa emp =queryEmpresa.getSingleResult();
        Set<Empleado> empleados = new HashSet<>(emp.getVendedores());
        empleados.addAll(queryAsesor.getResultList());

        return empleados;
    }

    @Override
    public Set<Empresa> getEmpresasAsesoradas(Integer id) {
        TypedQuery<Asesor> query = entityManager
                .createQuery("SELECT a FROM Asesor a WHERE a.id = :idAsesor",Asesor.class);
        query.setParameter("idAsesor",id);

        Asesor asesor = query.getSingleResult();

        Set<Empresa> empresasAsesoradas = new HashSet<>();

        // Se realiza una segunda consulta para cargar la colección debido a Hibernate podría ignorar la carga perezosa LAZY
        // y cargar todas las entidades relacionadas de inmediato.
        // En lugar de eso, se realiza una segunda consulta para obtener las empresas asociadas.
        List<AsesorEmpresa> ae = entityManager.createQuery(
                        "SELECT ae FROM AsesorEmpresa ae WHERE ae.asesor = :asesor", AsesorEmpresa.class)
                .setParameter("asesor", asesor)
                .getResultList();

        Set<AsesorEmpresa> listado = new HashSet<>(ae); //JPA siempre devuelve un tipo ArrayList.

        for (AsesorEmpresa element : listado) {
            empresasAsesoradas.add(element.getEmpresa());
        }

        return empresasAsesoradas;
    }

    @Override
    public LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa) {
        TypedQuery<Asesor> queryAsesor = entityManager
                .createQuery("SELECT a FROM Asesor a WHERE a.id = :idAsesor",Asesor.class);
        queryAsesor.setParameter("idAsesor",idAsesor);

        TypedQuery<Empresa> queryEmpresa = entityManager
                .createQuery("SELECT e FROM Empresa e WHERE e.id = :idEmpresa",Empresa.class);
        queryEmpresa.setParameter("idEmpresa",idEmpresa);


        Asesor asesor = queryAsesor.getSingleResult();
        Empresa empresa = queryEmpresa.getSingleResult();

        AsesorEmpresa ae = entityManager.createQuery(
                        "SELECT ae FROM AsesorEmpresa ae WHERE ae.asesor = :asesor AND ae.empresa = :empresa ", AsesorEmpresa.class)
                .setParameter("asesor", asesor)
                .setParameter("empresa", empresa)
                .getSingleResult();

        return ae.getFechaInicio();
    }

    @Override
    public Set<Vendedor> getVendedoresCaptados(Integer idPadre) {
        TypedQuery<Vendedor> query = entityManager
                .createQuery("SELECT v FROM Vendedor v WHERE v.id = :idPadre", Vendedor.class);
                query.setParameter("idPadre",idPadre);


        Vendedor vendedor = query.getSingleResult();

        Set<Vendedor> vendedoresCaptados = new HashSet<>();

        List<VendedorCaptado> vc = entityManager.createQuery(
                        "SELECT vc FROM VendedorCaptado vc WHERE vc.vendedorPadre = :vendedor", VendedorCaptado.class)
                .setParameter("vendedor", vendedor)
                .getResultList();

        for (VendedorCaptado element : vc) {
            vendedoresCaptados.add(element.getVendedorCaptados());
        }

        return vendedoresCaptados;
    }

    @Override
    public LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado) {
        TypedQuery<Vendedor> queryPadre = entityManager
                .createQuery("SELECT v FROM Vendedor v WHERE v.id = :idPadre", Vendedor.class);
        queryPadre.setParameter("idPadre",idPadre);
        Vendedor vendedorPadre = queryPadre.getSingleResult();

        TypedQuery<Vendedor> queryCaptado = entityManager
                .createQuery("SELECT v FROM Vendedor v WHERE v.id = :idCaptado", Vendedor.class);
        queryCaptado.setParameter("idCaptado",idCaptado);
        Vendedor captado = queryCaptado.getSingleResult();

        VendedorCaptado fechaCaptado = entityManager.createQuery(
                        "SELECT vc FROM VendedorCaptado vc WHERE vc.vendedorPadre = :vendedorPadre AND vc.vendedorCaptados = :captado ", VendedorCaptado.class)
                .setParameter("vendedorPadre", vendedorPadre)
                .setParameter("captado", captado)
                .getSingleResult();

        return fechaCaptado.getFechaCaptado();
    }

    @Override
    @Transactional
    public void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado) {

        //obtengo el objeto de ambos vendedores
        Vendedor vendedor = (Vendedor) getById(idVendedor);
        Vendedor vendedorCaptado = (Vendedor) getById(idVendedorCaptado);

        //Creo una instancia de VendedorCaptado
        VendedorCaptadoId vcId = new VendedorCaptadoId(idVendedor,idVendedorCaptado);
        VendedorCaptado vc = new VendedorCaptado(vcId,vendedor,vendedorCaptado,fechaCaptado);

        //Obtengo el set de VendedorCaptado del vendedor "padre"
        Set<VendedorCaptado> vendedoresCaptados = vendedor.getVendedoresCaptados();

        if(!vendedoresCaptados.contains(vc)){
            vendedor.agregarVendedorCaptado(vc);
            save(vc);
            update(vendedor);
            update(vendedorCaptado);
        }
        else{
            //todo: manejar exception de vendedor ya captado
            System.out.println("El vendedor ya está captado");
        }

    }

    @Override
    @Transactional
    public void save(Empleado emp) {
        entityManager.persist(emp);
    }

    @Override
    public void save(VendedorCaptado vc) {
        entityManager.persist(vc);
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
