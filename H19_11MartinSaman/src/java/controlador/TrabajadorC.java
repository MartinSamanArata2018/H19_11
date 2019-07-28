package controlador;

import dao.TrabajadorImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import modelo.Login;
import modelo.Trabajador;

@Named(value = "trabajadorC")
@SessionScoped
public class TrabajadorC implements Serializable {

    Trabajador trabajador, trabajadorSeleccionado;
    TrabajadorImpl daoTrabajador;
    List<Trabajador> listaTrabajador;
    @ManagedProperty("#{loginC}")
    LoginC loginC = new LoginC();

    public TrabajadorC() {
        trabajador = new Trabajador();
        daoTrabajador = new TrabajadorImpl();
        trabajadorSeleccionado = new Trabajador();
        listaTrabajador = new ArrayList<>();
    }

    @PostConstruct
    public void onInit() {
        try {
            listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listar() throws Exception {
        try {
            listaTrabajador = daoTrabajador.listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrar(Login login) throws Exception {
        try {
            if (login.getTIPLOG().equals("J")) {
                trabajador.setSucursal(login.getTrabajador().getSucursal());
            }
            daoTrabajador.registrar(trabajador);
            listar();            
            loginC.registrar(listaTrabajador.get(listaTrabajador.size() - 1));
            trabajador.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() throws Exception {
        try {
            if (trabajadorSeleccionado.getFECFINTRAB() != null) {
                eliminar();
                return;
            }
            daoTrabajador.editar(trabajadorSeleccionado);
            loginC.editar(trabajadorSeleccionado);
            listar();
            trabajadorSeleccionado.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() throws Exception {
        try {
            daoTrabajador.eliminar(trabajadorSeleccionado);
            loginC.eliminar(trabajadorSeleccionado);
            listar();
            trabajadorSeleccionado.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Trabajador getTrabajadorSeleccionado() {
        return trabajadorSeleccionado;
    }

    public void setTrabajadorSeleccionado(Trabajador trabajadorSeleccionado) {
        this.trabajadorSeleccionado = trabajadorSeleccionado;
    }

    public List<Trabajador> getListaTrabajador() {
        return listaTrabajador;
    }

    public void setListaTrabajador(List<Trabajador> listaTrabajador) {
        this.listaTrabajador = listaTrabajador;
    }

    public LoginC getLoginC() {
        return loginC;
    }

    public void setLoginC(LoginC loginC) {
        this.loginC = loginC;
    }

}
