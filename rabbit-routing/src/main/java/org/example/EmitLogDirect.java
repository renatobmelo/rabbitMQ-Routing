package org.example;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String MEU_NOME = "Renato Melo"; //Aqui está o nome, Professor.

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String severity = getSeverity(argv);
            String message = getMessage(argv);
            String mensagemComNome = message + " - Enviado por " + MEU_NOME;

            channel.basicPublish(EXCHANGE_NAME, severity, null, mensagemComNome.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity + "':'" + mensagemComNome + "'");
        }
    }

    private static String getSeverity(String[] argv) {
        return (argv.length >= 1) ? argv[0] : "info";
    }

    private static String getMessage(String[] argv) {
        return (argv.length >= 2) ? argv[1] : "Default Message";
    }
}
