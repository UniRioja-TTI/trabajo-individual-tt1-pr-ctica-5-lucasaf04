package servicios;

import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
class ContactoSim implements InterfazContactoSim {
    private DatosSolicitud datosSolicitud;

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        datosSolicitud = sol;
        Random rand = new Random();
        return rand.nextInt();
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        return null;
    }

    @Override
    public List<Entidad> getEntities() {
        Entidad e1 = new Entidad();
        e1.setId(1);
        e1.setName("E1");
        e1.setDescripcion("La entidad 1");
        Entidad e2 = new Entidad();
        e2.setId(2);
        e2.setName("E2");
        e2.setDescripcion("La entidad 2");
        Entidad e3 = new Entidad();
        e3.setId(3);
        e3.setName("E3");
        e3.setDescripcion("La entidad 3");
        Entidad e4 = new Entidad();
        e4.setId(4);
        e4.setName("E4");
        e4.setDescripcion("La entidad 4");
        return List.of(e1, e2, e3, e4);
    }

    @Override
    public boolean isValidEntityId(int id) {
        return true;
    }
}
