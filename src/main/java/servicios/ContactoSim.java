package servicios;

import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import modelo.Punto;
import openapi.api.ResultadosApi;
import openapi.api.SolicitudApi;
import openapi.model.ResultsResponse;
import openapi.model.Solicitud;
import openapi.model.SolicitudResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
class ContactoSim implements InterfazContactoSim {
    private final SolicitudApi api;
    private final String usuario = "usuario";

    private final List<Entidad> entidades = new ArrayList<>();

    public ContactoSim() {
        this.api = new SolicitudApi();

        entidades.add(crearEntidad(1, "Entidad A"));
        entidades.add(crearEntidad(2, "Entidad B"));
        entidades.add(crearEntidad(3, "Entidad C"));
        entidades.add(crearEntidad(4, "Entidad D"));
    }

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        Solicitud solicitud = new Solicitud();

        List<Integer> cantidades = new ArrayList<>();
        List<String> nombres = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : sol.getNums().entrySet()) {
            int id = entry.getKey();
            int cantidad = entry.getValue();

            Entidad entidad = findEntidadById(id);

            if (entidad != null) {
                cantidades.add(cantidad);
                nombres.add(entidad.getName());
            }
        }

        solicitud.setCantidadesIniciales(cantidades);
        solicitud.setNombreEntidades(nombres);

        try {
            SolicitudResponse response = api.solicitudSolicitarPost(usuario, solicitud);

            if (response.getDone() != null && response.getDone()) {
                return response.getTokenSolicitud();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public DatosSimulation descargarDatos(int tok) {
        try {
            ResultadosApi resultadosApi = new ResultadosApi();

            ResultsResponse response = resultadosApi.resultadosPost(usuario, tok);

            if (response.getDone() == null || !response.getDone()) {
                return null;
            }

            String data = response.getData();
            if (data == null || data.isEmpty()) {
                return null;
            }

            String[] lineas = data.split("\\n");

            DatosSimulation ds = new DatosSimulation();

            int ancho = Integer.parseInt(lineas[0].trim());
            ds.setAnchoTablero(ancho);

            Map<Integer, List<Punto>> puntos = new HashMap<>();
            int maxTiempo = 0;

            for (int i = 1; i < lineas.length; i++) {
                String[] partes = lineas[i].trim().split(",");

                int tiempo = Integer.parseInt(partes[0]);
                int y = Integer.parseInt(partes[1]);
                int x = Integer.parseInt(partes[2]);
                String color = partes[3];

                Punto p = new Punto();
                p.setX(x);
                p.setY(y);
                p.setColor(color);

                puntos.computeIfAbsent(tiempo, k -> new ArrayList<>()).add(p);

                if (tiempo > maxTiempo) {
                    maxTiempo = tiempo;
                }
            }

            ds.setPuntos(puntos);
            ds.setMaxSegundos(maxTiempo);

            return ds;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Entidad> getEntities() {
        return entidades;
    }

    @Override
    public boolean isValidEntityId(int id) {
        return entidades.stream().anyMatch(e -> e.getId() == id);
    }

    private Entidad crearEntidad(int id, String nombre) {
        Entidad e = new Entidad();
        e.setId(id);
        e.setName(nombre);
        e.setDescripcion(nombre);
        return e;
    }

    private Entidad findEntidadById(int id) {
        return entidades.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
