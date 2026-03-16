package servicios;

import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
class EnviarEmails implements InterfazEnviarEmails {
    private final Logger logger;

    public EnviarEmails(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean enviarEmail(Destinatario dest, String email) {
        logger.debug("Enviando email: {}", email);
        return true;
    }
}
