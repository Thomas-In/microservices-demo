package hipstershop;

import com.gremlin.*;

import java.io.IOException;

/**
 * Created by jslim
 */
public final class Gremlin {

    private static final Gremlin gremlin = new Gremlin();
    static Gremlin getInstance() {
        return gremlin;
    }
    void setting() {
        final GremlinCoordinatesProvider coordinatesProvider = new GremlinCoordinatesProvider() {
            @Override
            public ApplicationCoordinates initializeApplicationCoordinates() {
                return new ApplicationCoordinates.Builder()
                        .withType("Adservice")
                        .withField("get", "Ads")
                        .build();
            }
        };
        final GremlinServiceFactory gremlinServiceFactory = new GremlinServiceFactory(coordinatesProvider);
        final GremlinService gremlinService = gremlinServiceFactory.getGremlinService();
        final TrafficCoordinates injectionPoint = new TrafficCoordinates.Builder()
                .withType("Adservice")
                .withField("conference", "Seoul")
                .build();
        gremlinService.applyImpact(injectionPoint);
        //logger.info("setting gremlin end ");
    }
}
