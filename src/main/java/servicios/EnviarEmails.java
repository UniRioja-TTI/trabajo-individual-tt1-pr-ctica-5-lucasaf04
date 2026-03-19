package servicios;

import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;
import openapi.api.EmailApi;
import openapi.model.EmailResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
class EnviarEmails implements InterfazEnviarEmails {
    private final EmailApi api;
    private final Logger logger;

    public EnviarEmails(Logger logger) {
        this.api = new EmailApi();
        this.logger = logger;
    }

    @Override
    public boolean enviarEmail(Destinatario dest, String email) {
        logger.debug("Enviando email: {}", email);
        return true;

        // todo: usar cuando esté disponible la implementación de Destinatario
        // try {
        //     EmailResponse response = api.emailPost(dest, email);

        //     return response.getDone() != null && response.getDone();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        // return false;
    }
}
