package pl.piomin.modulith.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class SpringActualContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        try {
            builder.withDetail("hostname", InetAddress.getLocalHost().getHostName());
            builder.withDetail("canonical-hostname", InetAddress.getLocalHost().getCanonicalHostName());
        } catch (UnknownHostException exception) {
            builder.withDetail("hostname", "unknown");
        }
    }
}