package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

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
    public Set<Empleado> getUsuariosgetCredencialesAll() {
        TypedQuery<Empleado> queryCredecial = entityManager.createQuery("SELECT c.usuario FROM Credencial c",Empleado.class);
        return new HashSet<>(queryCredecial.getResultList());
    }

    @Override
    public Set<Administrador> getAdministradores() {
        TypedQuery<Administrador> query = entityManager.createQuery("SELECT a FROM Administrador a WHERE a.eliminado = 0 ORDER BY a.apellido, a.nombre ASC ", Administrador.class);

        return new TreeSet<>(query.getResultList());
    }

    @Override
    public Set<Asesor> getAsesores() {
        TypedQuery<Asesor> query = entityManager.createQuery("SELECT a FROM Asesor a ORDER BY a.apellido,a.nombre ASC", Asesor.class);

        return new LinkedHashSet<>(query.getResultList());
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
    public Set<AsesorEmpresa> getEmpresasAsesoradas(Integer id) {
        TypedQuery<Asesor> query = entityManager
                .createQuery("SELECT a FROM Asesor a WHERE a.id = :idAsesor",Asesor.class);
        query.setParameter("idAsesor",id);

        Asesor asesor = query.getSingleResult();

        // Se realiza una segunda consulta para obtener las empresas asociadas.
        List<AsesorEmpresa> ae = entityManager.createQuery(
                        "SELECT ae FROM AsesorEmpresa ae WHERE ae.asesor = :asesor ORDER BY ae.empresa.nombre", AsesorEmpresa.class)
                .setParameter("asesor", asesor)
                .getResultList();

        return new HashSet<>(ae); //JPA siempre devuelve un tipo ArrayList.

    }

    @Override
    public Set<AreasMercado> getAreasAsesoradasPorAsesor(Integer idAsesor) {
        TypedQuery<AreasMercado> query = entityManager.createQuery("SELECT a.areasAsesoradas FROM Asesor a WHERE a.id = :idAsesor", AreasMercado.class);
        query.setParameter("idAsesor",idAsesor);
        return new TreeSet<>(query.getResultList());
    }

    @Override
    public Set<Vendedor> getVendedores() {
        TypedQuery<Vendedor> query = entityManager.createQuery("SELECT v FROM Vendedor v ORDER BY v.apellido,v.nombre ASC", Vendedor.class);
        return new LinkedHashSet<>(query.getResultList());
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
    public Set<VendedorCaptado> getVendedoresCaptados(Integer idPadre) {
        List<VendedorCaptado> vc = null;
        try{
            //rescato al vendedor padre
            TypedQuery<Vendedor> query = entityManager
                    .createQuery("SELECT v FROM Vendedor v WHERE v.id = :idPadre", Vendedor.class);
            query.setParameter("idPadre",idPadre);

            Vendedor vendedor = query.getSingleResult();

            //Obtengo una lista de VendedorCaptodo
            vc = entityManager.createQuery(
                            "SELECT vc FROM VendedorCaptado vc WHERE vc.vendedorPadre = :vendedor", VendedorCaptado.class)
                    .setParameter("vendedor", vendedor)
                    .getResultList();
        }catch (Exception e){

        }


        //Devuelvo el Set de VendedorCaptado
        return new HashSet<>(vc);
    }

    @Override
    public VendedorCaptado getVendedorCaptado(Integer idPadre, Integer idVendCaptado) {
        TypedQuery<VendedorCaptado> query = entityManager
                .createQuery("SELECT vc FROM VendedorCaptado vc " +
                        "WHERE vc.vendedorPadre.id = :idPadre AND " +
                        "vc.vendedorCaptado.id = :idVendCaptado",VendedorCaptado.class);
        query.setParameter("idPadre",idPadre);
        query.setParameter("idVendCaptado",idVendCaptado);

        return query.getSingleResult();
    }

    @Override
    public Vendedor getCaptadorDelVendedor(Integer idCaptado) {

        Vendedor v = null;

        try{
            TypedQuery<Vendedor> query = entityManager
                    .createQuery("SELECT vc.vendedorPadre FROM VendedorCaptado vc WHERE vc.vendedorCaptado.id = :idCaptado ", Vendedor.class);
            query.setParameter("idCaptado",idCaptado);

            v = query.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }

        return v;
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
    public void eliminarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado) {
        //obtengo el vendedor y le seteo los vendedores captados
        Vendedor vendedor = (Vendedor) getById(idVendedor);

        vendedor.setVendedoresCaptados(getVendedoresCaptados(idVendedor));

        VendedorCaptado vc = getVendedorCaptado(idVendedor,idVendedorCaptado);

        vendedor.eliminarVendedorCaptado(vc);

        entityManager.remove(vc);
        entityManager.merge(vendedor);

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
    public void delete(Integer id) {
        Empleado emp = getById(id);
        emp.setEliminado(1);
        entityManager.merge(emp);
    }

    @Override
    @Transactional
    public void savePass(Credencial password){
        entityManager.persist(password);
    }

    @Override
    @Transactional
    public void updatePass(Credencial password){
        entityManager.merge(password);
    }

    @Override
    @Transactional
    public void deletePass(Credencial password){
        entityManager.remove(password);
    }

    @Override
    public Credencial getCredencial(Empleado usuario){
        TypedQuery<Credencial> query = entityManager
        .createQuery("SELECT s FROM Credencial s WHERE s.usuario = :usuario", Credencial.class);
        query.setParameter("usuario",usuario);

        //manejamos la excepcion en caso de no encontrar usuario o contrasenia
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Devolver una contraseña predeterminada o lanzar una excepción
            System.out.println("no se halló credencial");
            return null; // O ajusta según tu lógica
        }

    }

    @Override
    public String getPass(Empleado usuario){
        //Mensaje de Log de auditoria para controlar los ingresos
        System.out.println("Se intenta loggear el usuario-ID: "+usuario.getNombre()+"-"+usuario.getId());
        TypedQuery<Credencial> query = entityManager
                .createQuery("SELECT s FROM Credencial s WHERE s.usuario = :usuario", Credencial.class);
        query.setParameter("usuario",usuario);

        //manejamos la excepcion en caso de no encontrar usuario o contrasenia
        try {
            Credencial password = query.getSingleResult();
            return password.getPassword();
        } catch (NoResultException e) {
            // Devolver una contraseña predeterminada o lanzar una excepción
            return "contraseñaPredeterminada"; // O ajusta según tu lógica
        }

    }

    @Override
    public String getRol(Empleado usuario) {

        //Mensaje de Log de auditoria para controlar los ingresos
        System.out.println("Se intenta loggear el usuario-ID: "+usuario.getNombre()+"-"+usuario.getId());
        TypedQuery<Credencial> query = entityManager
                .createQuery("SELECT s FROM Credencial s WHERE s.usuario = :usuario", Credencial.class);
        query.setParameter("usuario",usuario);

        //manejamos la excepcion en caso de no encontrar usuario o contrasenia
        try {
            Credencial rol = query.getSingleResult();
            return rol.getRol();
        } catch (NoResultException e) {
            if (usuario instanceof Administrador) {
                return "ADM";
            }

            if (usuario instanceof Vendedor) {
                return "VEND";
            }

            if (usuario instanceof Asesor) {
                return "ASES";
            }else{
                // Devolver una contraseña predeterminada o lanzar una excepción
                return "contraseñaPredeterminada"; // O ajusta según tu lógica
            }

        }
    }

    @Override
    @Transactional
    public void eliminarTodosLosVendedoresCaptados(Integer idVendedor) {
        Vendedor vend = (Vendedor) getById(idVendedor);

        Set<VendedorCaptado> vc = vend.getVendedoresCaptados();

        //elimino los registros que relacionan al vendedor con el captador
        for(VendedorCaptado v : vc){
            entityManager.remove(v);
        }

        //elimino el listado de vendedores captados del vendedor
        vend.eliminarVendedoresCaptados();

        entityManager.merge(vend);
    }
}
