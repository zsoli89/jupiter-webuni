package hu.webuni.spring.jupiterwebuni.xmlws;

import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
    //  fontos honnan importalom
    private final Bus bus;
    private final TimeTableWs timetableWs;

    @Bean
    public Endpoint endPoint() {
        EndpointImpl endpointImpl = new EndpointImpl(bus, timetableWs);
        endpointImpl.publish("/timetable");
        return endpointImpl;
    }
}
