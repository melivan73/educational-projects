package airport;

import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static long findCountAircraftWithModelAirbus(Airport airport, String model) {
        //TODO Метод должен вернуть количество самолетов указанной модели.
        // подходят те самолеты, у которых name начинается со строки model
        return airport.getAllAircrafts().stream()
                .filter(aircraft -> aircraft.getModel().indexOf(model) == 0)
                .count();
    }

    public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {
        //TODO Метод должен вернуть словарь с количеством припаркованных самолетов в каждом терминале.
        return airport.getTerminals().stream()
                .collect(Collectors.toMap(Terminal::getName,
                        terminal -> terminal.getParkedAircrafts().size()));
    }

    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
        //TODO Метод должен вернуть список отправляющихся рейсов в ближайшее количество часов.
        List<Terminal> terminals = airport.getTerminals();
        List<Flight> result = new ArrayList<>();

        for (Terminal terminal : terminals) {
            result.addAll(terminal.getFlights().stream()
                    .filter(flight -> flight.getType() == Flight.Type.DEPARTURE &&
                            // в Airport.generateRandomDate используется Instant а не LocalDateTime
                            // в реализации уродливая добавка часов в секундах (вместо .plusHours(1))
                            flight.getDate().isBefore(Instant.now().plusSeconds(hours * 3600L)) &&
                            flight.getDate().isAfter(Instant.now()))
                    .toList());
        }
        return result;
    }

    public static Optional<Flight> findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        //TODO Найти ближайший прилет в указанный терминал.
        Optional<Terminal> optionalTerminal = airport.getTerminals().stream()
                .filter(terminal1 -> terminal1.getName().equals(terminalName))
                .findFirst();
        if (optionalTerminal.isEmpty()) {
            return Optional.empty();
        }
        Terminal terminal = optionalTerminal.get();
        return terminal.getFlights().stream()
                .filter(flight -> flight.getType() == Flight.Type.ARRIVAL &&
                        flight.getDate().isAfter(Instant.now()))
                .min((Comparator.comparing(Flight::getDate)));
    }
}